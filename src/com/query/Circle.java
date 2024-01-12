package com.query;

import java.awt.Graphics;

import com.rendering.Camera;
import com.util.EMath;

public class Circle extends Collider {

	public Circle() {
		this(0, 0, 10);
	}

	public Circle(double x, double y, double radius) {
		super(x, y, 2 * radius, 2 * radius);
		setType(Type.Circle);

	}

	public Circle(Vector2 position, double radius) {
		this(position.getX(), position.getY(), radius);
	}

	@Override
	public void draw(Graphics g, Camera cam) {
		Vector2 screenPosition = cam.toScreenSpace(position);
		int diameter = (int) size.getX();

		g.drawOval((int) screenPosition.getX(), (int) screenPosition.getY(), diameter, diameter);
	}

	@Override
	public Vector2 closestPoint(Collider other) {
		if (other instanceof Circle) {
			return closestPoint((Circle) other);
		} else {
			return closestPoint((Rect) other);
		}
	}

	public Vector2 closestPoint(Circle other) {
		Vector2 center = getCenter();
		double otherRadius = other.getSize().getX() * 0.5;
		Vector2 otherCenter = other.getPosition().add(otherRadius, otherRadius);
		Vector2 direction = otherCenter.subtract(center).getNormal();
		Vector2 pointOnOther = otherCenter.subtract(direction.scale(otherRadius));
		return pointOnOther;
	}

	public Vector2 closestPoint(Rect rect) {
		Vector2 rectMin = rect.getPosition();
		Vector2 rectMax = rectMin.add(rect.getSize());
		Vector2 clamped = EMath.clamp(getCenter(), rectMin, rectMax);
		Vector2 d1 = clamped.subtract(rectMin);
		Vector2 d2 = rectMax.subtract(clamped);
		double dx = Math.min(d1.getX(), d2.getX());
		double dy = Math.min(d1.getY(), d2.getY());

		double x = d1.getX() < d2.getX() ? rectMin.getX() : rectMax.getX();
		double y = d1.getY() < d2.getY() ? rectMin.getY() : rectMax.getY();

		Vector2 boundaryPoint = new Vector2(
				dy < dx ? clamped.getX() : x,
				dy < dx ? y : clamped.getY());

		return boundaryPoint;
	}

}
