package rendering;

import java.awt.Graphics;
import java.util.ArrayList;

import query.Vector2;
import tilemap.TileLayer;

public class Renderer implements Drawable {
    public Camera camera;
    ArrayList<Renderable> renderables;

    private class TileLayerRenderable extends Renderable {
        public TileLayer layer;
        public int tileSize;
        private int numRows, numCols;

        public TileLayerRenderable(TileLayer layer, int tileSize, int zIndex) {
            setzIndex(zIndex);
            this.tileSize = tileSize;
            this.layer = layer;
            this.numRows = layer.numRows;
            this.numCols = layer.numCols;
        }

        @Override
        public void draw(Graphics g, Camera cam) {
            Vector2 camMin = cam.getPosition();
            Vector2 camMax = cam.getPosition().add(cam.getSize());
            int startRow = Math.floorDiv((int) camMin.getY(), tileSize);
            int startCol = Math.floorDiv((int) camMin.getX(), tileSize);
            int endRow = Math.floorDiv((int) camMax.getY(), tileSize);
            int endCol = Math.floorDiv((int) camMax.getX(), tileSize);

            if (endCol < 0 || endRow < 0 || startCol >= numCols || startRow >= numRows) {
                // camera cant see tilemap anyway
                return;
            }

            int camX = (int) camMin.getX();
            int camY = (int) camMin.getY();

            // clamp values to grid values
            startRow = Math.max(startRow, 0);
            startCol = Math.max(startCol, 0);
            endRow = Math.min(endRow + 1, numRows);
            endCol = Math.min(endCol + 1, numCols);
            
            for (int row = startRow; row < endRow; row++) {
                for (int col = startCol; col < endCol; col++) {
                    Tile tile = layer.getTile(row, col);
                    
                    if (tile == null) {
                        continue;
                    }
                    
                    int x = col * tileSize - camX;
                    int y = row * tileSize - camY;
                    tile.draw(g, x, y, tileSize, tileSize);
                }
            }
        }

        @Override
        public void draw(Graphics g, int x, int y) {
        }

        @Override
        public void draw(Graphics g, int x, int y, int w, int h) {
        }

        @Override
        public int getWidth() {
            return 1;
        }

        @Override
        public int getHeight() {
            return 1;
        }

    }

    public Renderer(Camera camera) {
        this.camera = camera;
        this.renderables = new ArrayList<>();
    }

    public void add(TileLayer layer, int tileSize, int zIndex) {
        add(new TileLayerRenderable(layer, tileSize, zIndex));
    }

    public void add(Renderable renderable) {
        renderables.add(renderable);
    }

    public void update(double deltaTime) {
        for (Renderable renderable : renderables) {
            renderable.update(deltaTime);
        }
    }

    @Override
    public void draw(Graphics g, Camera cam) {
        for (int i = 0; i < renderables.size(); i++) {
            renderables.get(i).draw(g, cam);
        }
    }

    
}
