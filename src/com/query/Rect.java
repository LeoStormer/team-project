package com.query;

public class Rect extends Collider {

	public Rect() {
		this(0, 0, 100, 100);
	}

	public Rect(double x, double y, double w, double h) {
		super(x, y, w, h);
		setType(Type.Rect);
	}

}
