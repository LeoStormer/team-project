package com.query;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.rendering.Camera;
import com.rendering.Drawable;

public class Physics implements Drawable {
    public ArrayList<Collider> colliders;
    public HashMap<Collider, MovementInfo> movementInfo;
    public Grid grid;
    private double fixedUpdateInterval;
    private double accumulator = 0;
    ArrayList<CollisionEntry> collisionEntries;

    private class CollisionEntry {
        public Collider collider;
        public Collider[] collisions;

        public CollisionEntry(Collider collider, Collider[] collisions) {
            this.collider = collider;
            this.collisions = collisions;
        }
    }

    private class MovementInfo {
        public Vector2 oldPosition;

        public MovementInfo(Vector2 oldPosition) {
            this(oldPosition, Vector2.ZERO);
        }

        public MovementInfo(Vector2 oldPosition, Vector2 displacement) {
            this.oldPosition = oldPosition;
        }

        public void setOldPosition(Vector2 oldPosition) {
            this.oldPosition = oldPosition;
        }
    }

    public Physics(double fixedUpdateInterval, double worldWidth, double worldHeight, int gridCellSize) {
        this.colliders = new ArrayList<>();
        this.movementInfo = new HashMap<>();
        this.grid = new Grid(0, 0, (int) worldWidth, (int) worldHeight, gridCellSize);
        this.fixedUpdateInterval = fixedUpdateInterval;
    }

    public Physics(double fixedUpdateInterval, double worldCenterX, double worldCenterY, double worldWidth,
            double worldHeight, int gridCellSize) {
        this.colliders = new ArrayList<>();
        this.movementInfo = new HashMap<>();
        this.grid = new Grid((int) (worldCenterX - worldWidth * 0.5d), (int) (worldCenterY - worldHeight * 0.5d),
                (int) worldWidth, (int) worldHeight, gridCellSize);
        this.fixedUpdateInterval = fixedUpdateInterval;
    }

    public void addCollider(Collider collider) {
        colliders.add(collider);
        movementInfo.put(collider, new MovementInfo(collider.getPosition()));
        grid.add(collider);
    }

    public void addColliders(Collider[] colliderCollection) {
        for (Collider collider : colliderCollection) {
            addCollider(collider);
        }
    }

    public void removeCollider(Collider collider) {
        colliders.remove(collider);
        movementInfo.remove(collider);
        grid.remove(collider);
    }

    public void update(double deltaTime) {
        accumulator += deltaTime;
        while (accumulator >= fixedUpdateInterval) {
            accumulator -= fixedUpdateInterval;
            fixedUpdate(fixedUpdateInterval);
        }
    }

    public void fixedUpdate(double deltaTime) {
        collisionEntries = new ArrayList<>(colliders.size());

        for (Collider collider : colliders) {
            // Update collider's position
            if (collider.anchored) {
                continue;
            }

            Vector2 velocity = collider.getVelocity().scale(deltaTime);// collider.anchored ? Vector2.ZERO :
                                                                       // collider.getVelocity().scale(deltaTime);
            MovementInfo info = movementInfo.get(collider);
            Vector2 oldPosition = info.oldPosition;
            Vector2 newPosition = collider.getPosition().add(velocity);
            collider.setPosition(newPosition);
            Vector2 displacement = newPosition.subtract(oldPosition);

            // Get bounding box that encompasses collider at current and previous position.
            Vector2 colliderSize = collider.getSize();
            double displacementX = displacement.getX();
            double displacementY = displacement.getY();
            double boundsX = oldPosition.getX() + displacementX * 0.5;
            double boundsY = oldPosition.getY() + displacementY * 0.5;
            double boundsWidth = colliderSize.getX() + Math.abs(displacementX);
            double boundsHeight = colliderSize.getY() + Math.abs(displacementY);
            Rect boundingBox = new Rect(boundsX, boundsY, boundsWidth, boundsHeight);
            // Search the grid for other colliders that are near this bounding box
            CollisionEntry entry = new CollisionEntry(collider, grid.query(boundingBox));

            // The only collider found was the current collider.
            if (entry.collisions.length == 1) {
                continue;
            }

            collisionEntries.add(entry);
        }

        // Do narrow phase to determine actual collisions.
        for (CollisionEntry entry : collisionEntries) {
            Vector2 position = entry.collider.getPosition();
            Vector2 displacement = position.subtract(movementInfo.get(entry.collider).oldPosition);

            Arrays.sort(entry.collisions, (Collider c1, Collider c2) -> {
                double squareMag1 = Vector2.dotProduct(displacement, c1.getPosition().subtract(position));
                double squareMmag2 = Vector2.dotProduct(displacement, c2.getPosition().subtract(position));
                return Double.compare(squareMag1, squareMmag2);
            });

            for (Collider c : entry.collisions) {
                if (c == entry.collider || !c.collisionGroup.canCollide(entry.collider.collisionGroup)
                        || !entry.collider.intersects(c)) {
                    continue;
                }

                entry.collider.resolveCollision(c);
            }
        }

        // Update record of collider's position.
        for (Collider collider : colliders) {
            movementInfo.get(collider).setOldPosition(collider.getPosition());
            grid.update(collider);
        }
    }

    @Override
    public void draw(Graphics g, Camera cam) {
        for (Collider collider : grid.query(cam)) {
            collider.draw(g, cam);
        }
    }
}
