package query;

import java.awt.Graphics;

import rendering.Camera;
import rendering.Drawable;

public class Rect implements Drawable {
	protected Vector2 position;
	protected Vector2 size;
	
	public Rect() {
		position = new Vector2();
		size = new Vector2(100, 100);
	}

	public Rect(double x, double y, double w, double h) {
		position = new Vector2(x, y);
		size = new Vector2(w, h);
	}
	
	public boolean intersects(double otherX, double otherY, double otherWidth, double otherHeight) {
		double x = position.getX();
		double y = position.getY();
		double endX = x + size.getX();
		double endY = y + size.getY();
		double otherEndX = otherX + otherWidth;
		double otherEndY = otherY + otherHeight;

		return !(endX < otherX || endY < otherY || otherEndX < x || otherEndY < y );
	}
	
	public boolean intersects(Rect other) {
		return intersects(other.position.getX(), other.position.getY(), other.size.getX(), other.size.getY());
	}

	public boolean intersects(double circleX, double circleY, double radius) {
		double rectX = position.getX();
		double rectY = position.getY();
		
		double closestX = Math.max(rectX, Math.min(rectX + size.getX(), circleX));
		double closestY = Math.max(rectY, Math.min(rectY + size.getY(), circleY));
		
		double dx = circleX - closestX;
		double dy = circleY - closestY;
		
		return dx * dx + dy * dy <= radius * radius;
	}
	
	public boolean intersects(Circle c) {
		return intersects(c.position.getX(), c.position.getY(), c.radius);
	}
	
	@Override
	public void draw(Graphics g, Camera cam) {
		int x = (int) (position.getX() - cam.position.getX());
		int y = (int) (position.getY() - cam.position.getY());
		
		g.drawRect(x, y, (int) size.getX(), (int) size.getY());
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Vector2 getSize() {
		return size;
	}

	public void setSize(Vector2 size) {
		this.size = size;
	}
	
}
