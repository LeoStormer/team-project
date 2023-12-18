package com.rendering;

import java.awt.Graphics;

/**
 * The base class for all objects rendered by the rendering engine.
 */
public abstract class Renderable implements Drawable {

    /**
     * Determines the draw order of renderables.
     * Renderables with a lower zIndex will be drawn first.
     */
    public int zIndex = 0;

    /**
     * Determines the draw order among renderables with equivalent zIndex.
     * Renderables with a lower yIndex will be drawn first.
     */
    public double yIndex = 0;

    public void update(double deltaTime) {
        
    }
    
    /**
     * Checks if the camera can see this renderable and if it should be drawn.
     * @param cam the camera we are checking against.
     * @return
     */
    public boolean shouldDraw(Camera cam) {
        return true;
    }

    /**
     * Draw the renderable at the top left corner of the screen.
     * @param g the Graphics object to draw with
     * @param cam the {@link Camera} object
     */
    @Override
    public void draw(Graphics g, Camera cam) {
        draw(g, 0, 0);
    };

    /**
     * Draws the renderable at the specified location of the screen.
     * @param g the Graphics object to draw with
     * @param x the x-coordinate in screen space
     * @param y the y-coordinate in screen space
     */
    public abstract void draw(Graphics g, int x, int y);

    /**
     * Draws the renderable at the specified location of the screen scaled to width w and height h.
     * @param g the Graphics object to draw with
     * @param x the x-coordinate in screen space
     * @param y the y-coordinate in screen space
     * @param w the pixel width of the result image
     * @param h the pixel height of the result image
     */
    public abstract void draw(Graphics g, int x, int y, int w, int h);

    /**
     * Draws a scaled version of the renderable at the specified location of the screen.
     * @param g the Graphics object to draw with
     * @param x the x-coordinate in screen space
     * @param y the y-coordinate in screen space
     * @param scale the amount to scale by
     */
    public void draw(Graphics g, int x, int y, int scale) {
        int w = getWidth() * scale;
        int h = getHeight() * scale;
        draw(g, x, y, w, h);
    }

    /**
     * @return {@link #zIndex zIndex}
     */
    public int getzIndex() {
        return zIndex;
    }

    /**
     * @return {@link #yIndex yIndex}
     */
    public double getyIndex() {
        return yIndex;
    }

    public abstract int getWidth();

    public abstract int getHeight();

    /**
     * Sets the {@link #zIndex zIndex} of the renderable.
     * @param zIndex
     */
    public void setzIndex(int zIndex) {
        this.zIndex = zIndex;
    }

    /**
     * Sets the {@link #yIndex yIndex} of the renderable.
     * @param yIndex
     */
    public void setyIndex(double yIndex) {
        this.yIndex = yIndex;
    }
}
