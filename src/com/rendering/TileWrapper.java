package com.rendering;

import java.awt.Graphics;

import com.query.Rect;
import com.query.Vector2;

/**
 * This class wraps around a Tile and allows it to be given specific data.
 * Instances of this class can be given specific data such as a position, size, zIndex, and etc.
 * The wrapped tile will be rendered with these attributes without altering the original tile.
 */
public class TileWrapper extends Renderable {
    
    public int x, y;
    public int width, height;
    private Tile tile;
    private Rect border;

    public TileWrapper(Tile tile, int tileSize) {
        this(tile, 0, 0, tileSize, tileSize);
    }

    public TileWrapper(Tile tile, int x, int y, int tileSize) {
        this(tile, x, y, tileSize, tileSize);
    }
    
    public TileWrapper(Tile tile, int x, int y, int width, int height) {
        this.tile = tile;
        this.width = width;
        this.height = height;
        this.x = x;
        setY(y);
    }

    
    @Override
    public boolean shouldDraw(Camera cam) {
        return cam.intersects(border);
    }

    @Override
    public void draw(Graphics g, Camera cam) {
        Vector2 screenPosition = cam.toScreenSpace(x, y);
        tile.draw(g, (int) screenPosition.getX(), (int) screenPosition.getY(), width, height);
    }

    @Override
    public void draw(Graphics g, int x, int y) {
        tile.draw(g, x, y, width, height);
    }

    @Override
    public void draw(Graphics g, int x, int y, int w, int h) {
        tile.draw(g, x, y, w, h);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
        border.setPosition(x, y);
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        border.setPosition(x, y);
        setyIndex(y + getHeight());
    }

    public void setPosition(int x, int y) {
        this.x = x;
        setY(y);
    }

    public void setWidth(int width) {
        this.width = width;
        border.setSize(width, height);
    }

    public void setHeight(int height) {
        this.height = height;
        border.setSize(width, height);
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        border.setSize(width, height);
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }
}
