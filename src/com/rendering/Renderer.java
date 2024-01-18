package com.rendering;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.tilemap.TileLayer;

/**
 * This class handles the rendering of all drawable objects. Every frame before
 * being drawn, drawables are checked to see if the camera can see them, if it
 * can't they aren't drawn. Then all the drawables that passed this check are
 * then sorted first by ZIndex then by YIndex. They are then drawn in this
 * order.
 */
public class Renderer {

    public Camera camera;

    private ArrayList<Drawable> drawables;

    Comparator<Drawable> drawableSorter = new Comparator<Drawable>() {

        @Override
        public int compare(Drawable r1, Drawable r2) {
            int zCompare = Integer.compare(r1.getZIndex(), r2.getZIndex());
            return zCompare != 0 ? zCompare : Double.compare(r1.getYIndex(), r2.getYIndex());
        }

    };

    public Renderer(Camera camera) {
        this.camera = camera;
        this.drawables = new ArrayList<>();
    }

    public void add(TileLayer layer, int tileSize, int zIndex) {
        for (int row = 0; row < layer.numRows; row++) {
            int y = row * tileSize;
            for (int col = 0; col < layer.numCols; col++) {
                Tile tile = layer.getTile(row, col);

                if (tile == null) {
                    continue;
                }

                TileWrapper tileWrapper = new TileWrapper(tile, col * tileSize, y, tileSize);
                tileWrapper.setZIndex(zIndex);

                add(tileWrapper);
            }
        }
    }

    public void add(Drawable drawable) {
        drawables.add(drawable);
    }

    public void update(double deltaTime) {
        for (Drawable drawable : drawables) {
            drawable.update(deltaTime);
        }
    }

    public void draw(Graphics g, Camera cam) {
        ArrayList<Drawable> toDraw = new ArrayList<Drawable>();

        for (int i = 0; i < drawables.size(); i++) {
            Drawable drawable = drawables.get(i);
            if (!drawable.shouldDraw(cam)) {
                continue;
            }

            toDraw.add(drawable);
        }

        Collections.sort(toDraw, drawableSorter);
        for (int i = 0; i < toDraw.size(); i++) {
            toDraw.get(i).draw(g, cam);
        }
    }

}
