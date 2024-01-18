package com.query;

import com.util.EMath;

public class Vector2 {

	private static final double EPSILON = 1E-6;

	/**
	 * The Zero Vector (0,0).
	 */
	public static final Vector2 ZERO = new Vector2();

	/**
	 * The X-Axis (1,0).
	 */
	public static final Vector2 XAXIS = new Vector2(1, 0);

	/**
	 * The Y-Axis (0,1).
	 */
	public static final Vector2 YAXIS = new Vector2(0, 1);

	public static double dotProduct(Vector2 v1, Vector2 v2) {
		return v1.x * v2.x + v1.y * v2.y;
	}

	private double x;

	private double y;

	/**
	 * Constructs the Zero Vector
	 */
	public Vector2() {
		this(0.0d, 0.0d);
	}

	/**
	 * Constructs the Vector (x,y).
	 * 
	 * @param x
	 * @param y
	 */
	public Vector2(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @return the x component of this Vector
	 */
	public double getX() {
		return x;
	}

	/**
	 * @return the y component of this vector
	 */
	public double getY() {
		return y;
	}

	/**
	 * @return the length of this vector
	 */
	public double getMagnitude() {
		return Math.sqrt(x * x + y * y);
	}

	/**
	 * @return the length of this vector squared
	 */
	public double getMagnitudeSquared() {
		return dotProduct(this, this);
	}

	/**
	 * @return a vector pointing in the same direction with a length of one
	 */
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

	/**
	 * @param destination
	 * @param alpha
	 * @return a vector that is between this and destination by the fraction alpha
	 */
	public Vector2 lerp(Vector2 destination, double alpha) {
		return new Vector2(EMath.interpolate(x, destination.x, alpha), EMath.interpolate(y, destination.y, alpha));
	}

	/**
	 * @param other
	 * @return {@code true} if the X and Y components of this are within {@link #EPSILON}
	 *         units of the corresponding component of other, {@code false}
	 *         otherwise
	 */
	public boolean equals(Vector2 other) {
		return Math.abs(x - other.x) < EPSILON && Math.abs(y - other.y) < EPSILON;
	}

	/**
	 * @param other
	 * @param epsilon
	 * @return {@code true} if the X and Y components of this are within epsilon
	 *         units of the corresponding component of other, {@code false}
	 *         otherwise
	 */
	public boolean equals(Vector2 other, double epsilon) {
		return Math.abs(x - other.x) < epsilon && Math.abs(y - other.y) < epsilon;
	}

	@Override
	public String toString() {
		return x + ", " + y;
	}

}
