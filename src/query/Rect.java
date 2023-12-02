package query;

import java.awt.Graphics;

import rendering.Camera;
import rendering.Drawable;

public class Rect implements Drawable {
	/* Vectors (x,y) for position, size(w,h), and velocity */
	public Vector2 position;
	public Vector2 size;
	public Vector2 velocity;
	
	double endX;
    double endY;
	
	public Rect() {
		position = new Vector2();
		size = new Vector2(100, 100);	
        velocity = new Vector2(0,0);
	}

	public Rect(double x, double y, double w, double h) {
		position = new Vector2(x, y);
		size = new Vector2(w, h);
        velocity = new Vector2(0,0);
	}
	
	
	/* Physics */
	
	public void physicsOFF() {
	    setVelocity(new Vector2(0, 0));
	}
	
	
	/* Rect Basic Movement */
	
	public void move() {
	    position = position.add(velocity.getX(), velocity.getY());
	}
	
	public void goLT(double dx) {
	    velocity = velocity.add(-dx, 0.0);
	}

	public void goRT(double dx) {
	    velocity = velocity.add(dx, 0.0);
	}

	public void goUP(double dy) {
	    velocity = velocity.add(0.0, -dy);
	}

	public void goDN(double dy) {
	    velocity = velocity.add(0.0, dy);
	}
	

	public boolean wasLeftOf(Rect other) {
		// X velocity
		double vx = velocity.getX();
		
		double x = position.getX();
		double w = size.getX();
		
		double other_x = other.position.getX();
		
		return x - vx <= other_x - w;	    
	}
	
	public boolean wasRightOf(Rect other) {
		double vx = velocity.getX();

		double x = position.getX();
		
		double other_x = other.position.getX();
		double other_w = other.size.getX();
	    
		return x - vx >= other_x + other_w - 1;
	}
	
	public boolean wasAbove(Rect other) {
		double vy = velocity.getY();

		double y = position.getY();
		double h = size.getY();
		double other_y = other.position.getY();
	    
		return y - vy <= (other_y - h + 1);
	}
	
	public boolean wasBelow(Rect other) {		
		//return y - vy > r.y + r.h - 1;
		double vy = velocity.getY();

		double y = position.getY();
		
		double other_y = other.position.getY();
		double other_h = other.size.getY();
	    
		return y - vy >= (other_y + other_h - 1);
	}
	
	public void pushLeftOf(Rect other) {
		double w = size.getX();
		double y = position.getY();
	    double other_x = other.position.getX();
	    
	    /* Formula: x = r.x - w - 1 */
	    position = new Vector2(other_x - w - 1, y);
	}

	
	public void pushRightOf(Rect other) {
	    double other_x = other.position.getX();
	    double other_w = other.size.getX();
	    double y = position.getY();
	    
	    /* Formula: x = r.x + r.w + 1 */
	    position = new Vector2(other_x + other_w + 1, y);
	}

	
	public void pushAbove(Rect other) {
		
		double x = position.getX();
		double h = size.getY();
	    double other_y = other.position.getY();
	    
	    /* Formula: y = r.y - h - 1 */
	    position = new Vector2(x, other_y - h - 1);
	}
	
	public void pushBelow(Rect other) {
		double x = position.getX();
	    double other_y = other.position.getY();
		double other_h = other.size.getY();
		
		/* Formula: y = r.y +  r.h + 1 */
	    position = new Vector2(x, other_y + other_h + 1);
	}
	
	
	public void pushedOutOf(Rect r)
	{
		if(wasLeftOf(r))    pushLeftOf(r);
		if(wasRightOf(r))   pushRightOf(r);
		if(wasAbove(r))     pushAbove(r);
		if(wasBelow(r))     pushBelow(r);
	}
	
	
	public boolean intersects(double otherX, double otherY, double otherWidth, double otherHeight) {
		
		// X and Y of rect
		double x = position.getX();
		double y = position.getY();
		
		// Endpoints of rect
		double endX = x + size.getX();
		double endY = y + size.getY();
		
		// X and Y of other rect
		double otherEndX = otherX + otherWidth;
		double otherEndY = otherY + otherHeight;

		// Returns boolean value if Rect 1 intersects Rect 2
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
	
	/*Getters and Setters*/

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
	
	public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public Vector2 getVelocity() {
        return velocity;
    }
	
}
