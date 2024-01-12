package com.query;

import com.util.EMath;

public class Rect extends Collider {

	public Rect() {
		this(0, 0, 100, 100);
	}

	public Rect(double x, double y, double w, double h) {
		super(x, y, w, h);
		setType(Type.Rect);
	}

	@Override
	public Vector2 closestPoint(Collider other) {
		if (other instanceof Circle) {
			return closestPoint((Circle) other);
		} else {
			return closestPoint((Rect) other);
		}
	}

	public Vector2 closestPoint(Circle circle) {
		Vector2 center = getCenter();
		double radius = circle.getSize().getX() * 0.5;
		Vector2 circleCenter = circle.getPosition().add(radius, radius);
		Vector2 direction = center.subtract(circleCenter).getNormal();

		return circleCenter.add(direction.scale(radius));
	}

	public Vector2 closestPoint(Rect other) {
		Vector2 min = other.getPosition();
		Vector2 max = min.add(other.getSize());
		Vector2 clamped = EMath.clamp(getCenter(), min, max);

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

}
