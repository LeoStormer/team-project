package com.query;

import java.awt.Graphics;

import com.rendering.Camera;

public class Circle extends Collider {

	/**
	 * Construct a Circle at the origin and a radius of 10.
	 */
	public Circle() {
		this(0, 0, 10);
	}

	/**
	 * Contruct a Circle with its center at (x,y) and a radius of r.
	 * @param x
	 * @param y
	 * @param r
	 */
	public Circle(double x, double y, double r) {
		super(x, y, 2 * r, 2 * r);

	}

	/**
	 * Construct a Circle with its center at position and a radius of r.
	 * @param position
	 * @param r
	 */
	public Circle(Vector2 position, double r) {
		this(position.getX(), position.getY(), r);
	}

	@Override
	public void draw(Graphics g, Camera cam) {
		Vector2 screenPosition = cam.toScreenSpace(position);
		int diameter = (int) size.getX();

		g.drawOval((int) screenPosition.getX(), (int) screenPosition.getY(), diameter, diameter);
	}

	@Override
	public void draw(Graphics g, int x, int y) {
		int radius = (int) size.getX();
		g.drawOval(x, y, radius, radius);
	}

	@Override
	public void draw(Graphics g, int x, int y, int width, int height) {
		int diameter = Math.min(width, height);
		g.drawOval(x, y, diameter, diameter);
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
