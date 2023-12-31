package com.rendering;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import com.query.Rect;
import com.query.Vector2;

public class Camera extends Rect implements ComponentListener {

	public Camera(double w, double h) {
		super(0, 0, w, h);
	}

	public Camera(double x, double y, double w, double h) {
		super(x, y, w, h);
	}

	public Vector2 toScreenSpace(Vector2 worldSpaceVector) {
		return worldSpaceVector.subtract(position);
	}

	public Vector2 toScreenSpace(double x, double y) {
		double screenX = x - position.getX();
		double screenY = y - position.getY();
		return new Vector2(screenX, screenY);
	}

	public Vector2 toWorldSpace(Vector2 screenSpacedVector) {
		return screenSpacedVector.add(position);
	}

	public Vector2 toWorldSpace(double screenX, double screenY) {
		double x = screenX + position.getX();
		double y = screenY + position.getY();
		return new Vector2(x, y);
	}

	@Override
	public void componentResized(ComponentEvent e) {
		Dimension size = e.getComponent().getSize();
		setSize(size.getWidth(), size.getHeight());
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void componentShown(ComponentEvent e) {
	}

	@Override
	public void componentHidden(ComponentEvent e) {
	}

}
