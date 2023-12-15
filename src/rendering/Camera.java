package rendering;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import query.Rect;
import query.Vector2;

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

	public Vector2 toWorldSpace(Vector2 screenSpacedVector) {
		return screenSpacedVector.add(position);
	}

	@Override
	public void componentResized(ComponentEvent e) {
		Dimension size = e.getComponent().getSize();
		setSize(size.getWidth(), size.getHeight());
	}

	@Override
	public void componentMoved(ComponentEvent e) {}

	@Override
	public void componentShown(ComponentEvent e) {}

	@Override
	public void componentHidden(ComponentEvent e) {}
}
