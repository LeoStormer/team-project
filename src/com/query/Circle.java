package com.query;

import java.awt.Graphics;

import com.rendering.Camera;

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

}
