package rendering;

import java.awt.Graphics;
import java.util.Vector;

public class DrawLayer implements Drawable{
	private Vector<Drawable> drawables;
	
	public DrawLayer() {
		drawables = new Vector<Drawable>();
	}
	
	public void add(Drawable d) {
		drawables.add(d);
	}

	public void remove(Drawable d) {
		drawables.remove(d);
	}
	
	@Override
	public void draw(Graphics g, Camera cam) {
		for (Drawable drawable: drawables) {
			drawable.draw(g, cam);
		}
	}
}
