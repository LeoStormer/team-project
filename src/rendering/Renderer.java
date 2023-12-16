package rendering;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import tilemap.TileLayer;

public class Renderer implements Drawable {
    public Camera camera;
    ArrayList<Renderable> renderables;

    Comparator<Renderable> renderableSorter = new Comparator<Renderable>() {
        @Override
        public int compare(Renderable r1, Renderable r2) {
            int zCompare = Integer.compare(r1.zIndex, r2.zIndex);
            return zCompare != 0 ? zCompare : Double.compare(r1.yIndex, r2.yIndex);
        }
        
    };

    public Renderer(Camera camera) {
        this.camera = camera;
        this.renderables = new ArrayList<>();
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
                tileWrapper.setzIndex(zIndex);
                tileWrapper.setyIndex(y);

                add(tileWrapper);
            }
        }
    }

    public void add(Renderable renderable) {
        renderables.add(renderable);
    }

    public void update(double deltaTime) {
        for (Renderable renderable : renderables) {
            renderable.update(deltaTime);
        }

        Collections.sort(renderables, renderableSorter);
    }

    @Override
    public void draw(Graphics g, Camera cam) {
        for (int i = 0; i < renderables.size(); i++) {
            Renderable renderable = renderables.get(i);
            if (!renderable.shouldDraw(cam)) {
                continue;
            }

            renderable.draw(g, cam);
        }
    }

}
