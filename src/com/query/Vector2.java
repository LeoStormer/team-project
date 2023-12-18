package com.query;

public class Vector2 {
	private static final double EPSILON = 1E-6;
	public static final Vector2 ZERO = new Vector2();
	public static final Vector2 XAXIS = new Vector2(1, 0);
	public static final Vector2 YAXIS = new Vector2(0, 1);

	private double x;
	private double y;
	
	public Vector2() {
		this(0.0d, 0.0d);
	}

	public Vector2(double x, double y) {
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
	
	public Vector2 getNormal() {
		double inverseMagnitude = 1.0 / getMagnitude();

		return new Vector2(x * inverseMagnitude, y * inverseMagnitude);
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
	
	public boolean equals(Vector2 other) {
		return Math.abs(x - other.x) < EPSILON && Math.abs(y - other.y) < EPSILON;
	}

	public boolean equals(Vector2 other, double epsilon) {
		return Math.abs(x - other.x) < epsilon && Math.abs(y - other.y) < epsilon;
	}

	@Override
	public String toString() {
		return x + ", " + y;
	}
}
