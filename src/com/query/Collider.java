package com.query;

import java.awt.Graphics;

import com.rendering.Camera;
import com.rendering.Drawable;

/**
 * The base class for physically simulated objects.
 */
public abstract class Collider implements Drawable {

    /**
     * The Top left corner of the bounding box encompassing the collider.
     */
    public Vector2 position;
    
    /**
     * The size of the bounding box encompassing the collider.
     */
    public Vector2 size;

    /**
     * The velocity of the collider.
     */
    public Vector2 velocity;

    /**
     * Determines whether the collider can be moved by physics.
     */
    public boolean anchored;

    /**
     * A reference to the {@link CollisionGroup} this collider belongs to.
     */
    public CollisionGroup collisionGroup;

    /**
     * Constructs a Collider at position (0,0) with size (100,100).
     */
    protected Collider() {
        this(0, 0, 100, 100);
    }

    /**
     * Constructs a Collider at position (x,y) with size (w,h)
     * @param x
     * @param y
     * @param w
     * @param h
     */
    protected Collider(double x, double y, double w, double h) {
        position = new Vector2(x, y);
        size = new Vector2(w, h);
        velocity = Vector2.ZERO;
        anchored = false;
        collisionGroup = CollisionGroup.DEFAULT;
    }

    /**
     * 
     * @param position
     * @return The point on this collider's perimeter closest to the input position.
     */
    public abstract Vector2 closestPointOnPerimeter(Vector2 position);

    /**
     * 
     * @param other
     * @return The point on the other collider's perimeter closest to this collider's center
     */
    public Vector2 closestPoint(Collider other) {
        return other.closestPointOnPerimeter(getCenter());
    }

    /**
     * 
     * @param other
     * @return Whether this collider is intersecting with the other collider.
     */
    public boolean intersects(Collider other) {
        if (other instanceof Circle) {
            return intersects((Circle) other);
        } else {
            return intersects((Rect) other);
        }
    }

    public abstract boolean intersects(Rect other);

    public abstract boolean intersects(Circle other);

    /**
     * Moves both this collider and the other so that they aren't colliding with each other.
     * @param other
     */
    public void resolveCollision(Collider other) {
        if (other instanceof Circle) {
            resolveCollision((Circle) other);
        } else {
            resolveCollision((Rect) other);
        }
    }

    public abstract void resolveCollision(Rect other);

    public abstract void resolveCollision(Circle other);

    /**
     * @return {@link Collider#position}
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * Moves the Collider such that the top left corner of its bounding box is at the new position.
     * Ignores the {@link Collider#anchored anchored} property.
     * @param newPosition
     */
    public void setPosition(Vector2 newPosition) {
        this.position = newPosition;
    }

    /**
     * Moves the Collider such that the top left corner of its bounding box is at the new position.
     * Ignores the {@link Collider#anchored anchored} property.
     * @param x the x-coordinate of the new position
     * @param y the y-coordinate of the new position
     */
    public void setPosition(double x, double y) {
        this.position = new Vector2(x, y);
    }

    /**
     * @return The size of the bounding box encompassing this collider
     */
    public Vector2 getSize() {
        return size;
    }

    /**
     * @param size the new size of the bounding box encompassing this collider
     */
    public void setSize(Vector2 size) {
        this.size = size;
    }

    /**
     * @param w the new width of the bounding box encompassing this collider
     * @param h the new height of the bounding box encompassing this collider
     */
    public void setSize(double w, double h) {
        this.size = new Vector2(w, h);
    }

    /**
     * @return The velocity of the collider
     */
    public Vector2 getVelocity() {
        return velocity;
    }

    /**
     * @param velocity the new velocity of the collider
     */
    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    /**
     * @param vx the new velocity in the x direction
     * @param vy the new velocity in the y direction
     */
    public void setVelocity(double vx, double vy) {
        this.velocity = new Vector2(vx, vy);
    }

    /**
     * @return Whether or not the collider can be moved by physics.
     */
    public boolean isAnchored() {
        return anchored;
    }

    /**
     * @param anchored whether or not the collider can be moved by physics.
     */
    public void setAnchored(boolean anchored) {
        this.anchored = anchored;
    }

    /**
     * @return The {@link CollisionGroup} this collider belongs to.
     */
    public CollisionGroup getCollisionGroup() {
        return collisionGroup;
    }

    /**
     * Sets the {@link CollisionGroup} this collider belongs to.
     * @param collisionGroup
     */
    public void setCollisionGroup(CollisionGroup collisionGroup) {
        this.collisionGroup = collisionGroup;
    }

    /**
     * Draws the bounding box that encompasses this collider.
     */
    @Override
    public void draw(Graphics g, Camera cam) {
        Vector2 screenPosition = cam.toScreenSpace(position);

        g.drawRect((int) screenPosition.getX(), (int) screenPosition.getY(), (int) size.getX(), (int) size.getY());

    }

    /**
     * @return The center of this collider.
     */
    public Vector2 getCenter() {
        return position.add(size.scale(0.5d));
    }

}
