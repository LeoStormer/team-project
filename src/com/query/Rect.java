package com.query;

import com.util.EMath;

public class Rect extends Collider {

	public Rect() {
		this(0, 0, 100, 100);
	}

	public Rect(double x, double y, double w, double h) {
		super(x, y, w, h);
	}

	@Override
	public Vector2 closestPointOnPerimeter(Vector2 position) {
		Vector2 min = getPosition();
		Vector2 max = min.add(getSize());
		Vector2 clamped = EMath.clamp(position, min, max);

		Vector2 d1 = clamped.subtract(min);
		Vector2 d2 = max.subtract(clamped);
		double dx = Math.min(d1.getX(), d2.getX());
		double dy = Math.min(d1.getY(), d2.getY());

		double x = d1.getX() < d2.getX() ? min.getX() : max.getX();
		double y = d1.getY() < d2.getY() ? min.getY() : max.getY();

		Vector2 boundaryPoint = new Vector2(
				dy < dx ? clamped.getX() : x,
				dy < dx ? y : clamped.getY());

		return boundaryPoint;
	}

	@Override
	public boolean intersects(Circle other) {
		double radius = other.getSize().getX() * 0.5d;
		Vector2 center = other.getPosition().add(radius, radius);
		return CollisionDetector.intersects(getPosition(), getSize(), center, radius);
	}

	@Override
	public boolean intersects(Rect other) {
		return CollisionDetector.intersects(getPosition(), getSize(), other.getPosition(), other.getSize());
	}

	@Override
	public void resolveCollision(Rect other) {
		Vector2[] newPositions = CollisionResolver.resolveCollision(getPosition(), getSize(), isAnchored(),
				other.getPosition(), other.getSize(), other.isAnchored());
		setPosition(newPositions[0]);
		other.setPosition(newPositions[1]);
	}

	@Override
	public void resolveCollision(Circle other) {
		Vector2[] newPositions = CollisionResolver.resolveCollision(getPosition(), getSize(), isAnchored(),
				other.getPosition(), other.getSize().getX() * 0.5, other.isAnchored());
		setPosition(newPositions[0]);
		other.setPosition(newPositions[1]);
	}

}
