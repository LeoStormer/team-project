package com.rendering;

import java.awt.Graphics;

/**
 * The base interface for all objects rendered by the rendering engine.
 */
public interface Drawable {

	/**
	 * @param camera
	 * @return Whether the camera sees this drawable and it should be drawn
	 */
	default boolean shouldDraw(Camera camera) {
		return true;
	};

	/**
	 * @return The primary value used to determine the draw order in the renderer. A
	 *         lower value = higher priority.
	 */
	default int getZIndex() {
		return 0;
	}

	/**
	 * @return The secondary value used to determine the draw order in the renderer.
	 *         A lower value = higher priority.
	 */
	default double getYIndex() {
		return 0;
	}

	/**
	 * Draw the drawable relative to the camera.
	 * 
	 * @param g   the Graphics object to draw with
	 * @param cam the {@link Camera} object
	 */
	public void draw(Graphics g, Camera cam);

	/**
	 * Draws the drawable at the specified location of the screen.
	 * 
	 * @param g the Graphics object to draw with
	 * @param x the x-coordinate in screen space
	 * @param y the y-coordinate in screen space
	 */
	public void draw(Graphics g, int x, int y);

	/**
	 * Draws the drawable at the specified location of the screen scaled to width w
	 * and height h.
	 * 
	 * @param g the Graphics object to draw with
	 * @param x the x-coordinate in screen space
	 * @param y the y-coordinate in screen space
	 * @param w the pixel width of the result image
	 * @param h the pixel height of the result image
	 */
	public void draw(Graphics g, int x, int y, int w, int h);

	default void update(double deltaTime) {

	}

}
