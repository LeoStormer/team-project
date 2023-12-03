package rendering;

import query.Rect;
import query.Vector2;

public class Camera extends Rect {
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
}
