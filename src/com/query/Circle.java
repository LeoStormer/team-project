package com.query;

import java.awt.Graphics;

import com.rendering.Camera;

public class Circle extends Collider {

	public Circle() {
		this(0, 0, 10);
	}

	public Circle(double x, double y, double radius) {
		super(x, y, 2 * radius, 2 * radius);

	}

	public Circle(Vector2 position, double radius) {
		this(position.getX(), position.getY(), radius);
	}

	/**
	 * Draws the circle.
	 */
	@Override
	public void draw(Graphics g, Camera cam) {
		Vector2 screenPosition = cam.toScreenSpace(position);
		int diameter = (int) size.getX();

		g.drawOval((int) screenPosition.getX(), (int) screenPosition.getY(), diameter, diameter);
	}

	@Override
	public Vector2 closestPointOnPerimeter(Vector2 position) {
		double radius = getSize().getX() * 0.5d;
		Vector2 center = getPosition().add(radius, radius);
		Vector2 direction = position.subtract(center);
		direction = direction.equals(Vector2.ZERO) ? Vector2.YAXIS : direction.getNormal();
		return center.add(direction.scale(radius));
	}

	@Override
	public boolean intersects(Circle other) {
		double radius = getSize().getX() * 0.5d;
		Vector2 center = getPosition().add(radius, radius);
		double otherRadius = other.getSize().getX() * 0.5;
		Vector2 otherCenter = other.getPosition().add(otherRadius, otherRadius);
		return CollisionDetector.intersects(center, radius, otherCenter, otherRadius);
	}

	@Override
	public boolean intersects(Rect other) {
		double radius = getSize().getX() * 0.5d;
		Vector2 center = getPosition().add(radius, radius);
		return CollisionDetector.intersects(other.getPosition(), other.getSize(), center, radius);
	}

	@Override
	public void resolveCollision(Circle other) {
		double radius = getSize().getX() * 0.5d;
		double otherRadius = other.getSize().getX() * 0.5d;
		Vector2[] newPositions = CollisionResolver.resolveCollision(getPosition(), radius, isAnchored(),
				other.getPosition(), otherRadius, other.isAnchored());
		setPosition(newPositions[0]);
		other.setPosition(newPositions[1]);
	}

	@Override
	public void resolveCollision(Rect other) {
		Vector2[] newPositions = CollisionResolver.resolveCollision(other.getPosition(), other.getSize(),
				other.isAnchored(), getPosition(), getSize().getX() * 0.5d, isAnchored());
		setPosition(newPositions[1]);
		other.setPosition(newPositions[0]);
	}

}
