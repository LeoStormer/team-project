package query;

public class Vector2 {

	private double x;
	private double y;
	
	public Vector2() {
		x = 0.0d;
		y = 0.0d;
	}

	public Vector2(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public static double dotProduct(Vector2 v1, Vector2 v2) {
		return v1.x * v2.x + v1.y * v2.y;
	}
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getMagnitude() {
		return Math.sqrt(x * x + y * y);
	}
	
	public Vector2 add(Vector2 other) {
		return new Vector2(x + other.x, y + other.y);
	}
	
	public Vector2 subtract(Vector2 other) {
		return new Vector2(x - other.x, y - other.y);
	}

	public Vector2 add(double dx, double dy) {
		return new Vector2(x + dx, y + dy);
	}
	
	public Vector2 subtract(double dx, double dy) {
		return new Vector2(x - dx, y - dy);
	}
	
	public Vector2 scale(Number number) {
		double scalar = number.doubleValue();
		return new Vector2(x * scalar, y * scalar);
	}
	
	public Vector2 divide(Number number) {
		double divisor = number.doubleValue();
		return new Vector2(x / divisor, y / divisor);
	}

	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = x;
	}
}
