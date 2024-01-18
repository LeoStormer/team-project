package com.rendering;

import java.awt.Graphics;

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

    /**
     * Draw the renderable at the top left corner of the screen.
     */
    @Override
    public void draw(Graphics g, Camera cam) {
        draw(g, 0, 0);
    };

    @Override
    public int getZIndex() {
        return zIndex;
    }

    @Override
    public double getYIndex() {
        return yIndex;
    }

    public abstract int getWidth();

    public abstract int getHeight();

    /**
     * Sets the {@link #zIndex zIndex} of the renderable.
     * 
     * @param zIndex
     */
    public void setZIndex(int zIndex) {
        this.zIndex = zIndex;
    }

    /**
     * Sets the {@link #yIndex yIndex} of the renderable.
     * 
     * @param yIndex
     */
    public void setYIndex(double yIndex) {
        this.yIndex = yIndex;
    }

}
