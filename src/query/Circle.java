package query;

import java.awt.Graphics;

import rendering.Camera;
import rendering.Drawable;

public class Circle implements Drawable {

	Vector2 position;
	double radius;

	public Circle() {
		position = new Vector2();
		radius = 1.0d;
	}

	public Circle(double x, double y, double radius) {
		this.position = new Vector2(x, y);
		this.radius = radius;
	}
	public Circle(Vector2 position, double radius) {
		this.position = position;
		this.radius = radius;
	}

	public boolean intersects(double otherX, double otherY, double otherRadius) {
		double dx = position.getX() - otherX;
		double dy = position.getY() - otherY;
		double totalRadius = radius + otherRadius;

		return dx * dx + dy * dy <= totalRadius * totalRadius;
	}

	public boolean intersects(Circle c) {
		return intersects(c.position.getX(), c.position.getY(), c.radius);
	}


	public boolean intersects(double rectX, double rectY, double rectW, double rectH) {
		double circleX = position.getX();
		double circleY = position.getY();
		
		double closestX = Math.max(rectX, Math.min(rectX + rectW, circleX));
		double closestY = Math.max(rectY, Math.min(rectY + rectH, circleY));
		
		double dx = circleX - closestX;
		double dy = circleY - closestY;
		
		return dx * dx + dy * dy <= radius * radius;
	}
	
	public boolean intersects(Rect r) {
		return intersects(r.position.getX(), r.position.getY(), r.size.getX(), r.size.getY());
	}

	public void draw(Graphics g, Camera cam) {
		int diameter = (int) radius * 2;
		int x = (int) (position.getX() - radius - cam.position.getX());
		int y = (int) (position.getY() - radius - cam.position.getY());
		
		g.drawOval(x, y, diameter, diameter);
	}
}
