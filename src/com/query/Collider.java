package com.query;

import java.awt.Graphics;

import com.rendering.Camera;
import com.rendering.Drawable;

public abstract class Collider implements Drawable {

    public Vector2 position; // Top left corner of the collider
    public Vector2 size;
    public Vector2 velocity;
    public boolean anchored; // is collider locked to current position
    public Type type;
    public CollisionGroup collisionGroup;

    public enum Type {
        Circle,
        Rect,
    }

    protected Collider() {
        this(0, 0, 100, 100);
    }

    protected Collider(double x, double y, double w, double h) {
        position = new Vector2(x, y);
        size = new Vector2(w, h);
        velocity = Vector2.ZERO;
        anchored = false;
        type = Type.Rect;
        collisionGroup = CollisionGroup.DEFAULT;
    }

    public boolean intersects(Collider other) {
        if (this.type == Type.Rect && other.type == Type.Rect) {
            return CollisionDetector.intersects(this.getPosition(), this.getSize(), other.getPosition(),
                    other.getSize());
        } else if (this.type == Type.Circle && other.type == Type.Circle) {
            double circle1Radius = this.getSize().getX() * 0.5d;
            Vector2 circle1Center = this.getPosition().add(circle1Radius, circle1Radius);
            double circle2Radius = other.getSize().getX() * 0.5;
            Vector2 circle2Center = other.getPosition().add(circle2Radius, circle2Radius);
            return CollisionDetector.intersects(circle1Center, circle1Radius, circle2Center, circle2Radius);
        } else {
            Collider rect = type == Type.Rect ? this : other;
            Collider circle = type == Type.Rect ? other : this;
            double circleRadius = circle.getSize().getX() * 0.5d;
            Vector2 circleCenter = circle.getPosition().add(circleRadius, circleRadius);
            return CollisionDetector.intersects(rect.getPosition(), rect.getSize(), circleCenter, circleRadius);
        }
    }

    public void resolveCollision(Collider other) {
        Vector2[] newPositions;
        if (this.type == Type.Rect && other.type == Type.Rect) {
            newPositions = CollisionResolver.resolveCollision(this.getPosition(), this.getSize(), this.anchored,
                    other.getPosition(), other.getSize(), other.anchored);
        } else if (this.type == Type.Circle && other.type == Type.Circle) {
            double circle1Radius = this.getSize().getX() * 0.5d;
            double circle2Radius = other.getSize().getX() * 0.5d;
            newPositions = CollisionResolver.resolveCollision(this.getPosition(), circle1Radius, this.anchored,
                    other.getPosition(), circle2Radius, other.anchored);
        } else {
            Collider rect = type == Type.Rect ? this : other;
            Collider circle = type == Type.Rect ? other : this;

            newPositions = CollisionResolver.resolveCollision(rect.getPosition(), rect.getSize(), rect.anchored,
                    circle.getPosition(), circle.getSize().getX() * 0.5, circle.anchored);

            // returns rect's newpPosition in newPosition[0],
            // so if (this) is a circle, swap them.
            if (type == Type.Circle) {
                Vector2 temp = newPositions[0];
                newPositions[0] = newPositions[1];
                newPositions[1] = temp;
            }
        }

        this.setPosition(newPositions[0]);
        other.setPosition(newPositions[1]);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setPosition(double x, double y) {
        this.position = new Vector2(x, y);
    }

    public Vector2 getSize() {
        return size;
    }

    public void setSize(Vector2 size) {
        this.size = size;
    }

    public void setSize(double w, double h) {
        this.size = new Vector2(w, h);
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public void setVelocity(double vx, double vy) {
        this.velocity = new Vector2(vx, vy);
    }

    public boolean isAnchored() {
        return anchored;
    }

    public void setAnchored(boolean anchored) {
        this.anchored = anchored;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public CollisionGroup getCollisionGroup() {
        return collisionGroup;
    }

    public void setCollisionGroup(CollisionGroup collisionGroup) {
        this.collisionGroup = collisionGroup;
    }

    @Override
    public void draw(Graphics g, Camera cam) {// Draw the rect that encompasses this collider
        Vector2 screenPosition = cam.toScreenSpace(position);

        g.drawRect((int) screenPosition.getX(), (int) screenPosition.getY(), (int) size.getX(), (int) size.getY());

    }

    public Vector2 getCenter() {
        return position.add(size.scale(0.5d));
    }

}
