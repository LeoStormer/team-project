
import java.util.ArrayList;

import query.Physics;
import query.Rect;
import rendering.Renderer;
import tilemap.CollisionLayer;
import tilemap.Layer;
import tilemap.TileLayer;
import tilemap.TileMap;
import tilemap.TileParser;

public final class TileMapLoader {
    private TileMap tileMap;
    private int tileSize;

    public TileMapLoader(String filePath, TileParser tileParser, int tileSize) {
        super();
        this.tileSize = tileSize;
        tileMap = new TileMap(filePath, tileParser, tileSize);
    }

    public Physics parseCollisionLayer() {
        // naive implementation
        // Loop through the collision layer and create a tileSize x tileSize Rect on the collidable tiles
        double worldWidth = tileMap.numTilesX * tileSize;
        double worldHeight = tileMap.numTilesY * tileSize;
        Physics physics = new Physics(1 / 60.0d, worldWidth, worldHeight, 128);

        CollisionLayer collisionLayer = (CollisionLayer) tileMap.getLayer(0);
        for (int row = 0; row < tileMap.numTilesY; row++) {
            for (int col = 0; col < tileMap.numTilesX; col++) {
                if (!collisionLayer.isCollidable(row, col)) {
                    continue;
                }
                Rect wall = new Rect(col * tileSize, row * tileSize, tileSize, tileSize);
                wall.setAnchored(true);
                physics.addCollider(wall);
            }
        }
        
        // Better implementation
        // Perform Greedy Mesh on the collisionLayer

        return physics;
    }

    public void parseTileLayers(Renderer renderer) {
        ArrayList<Layer> layers = tileMap.getLayers();
        for (int layerIndex = 1; layerIndex < layers.size(); layerIndex++) {
            TileLayer layer = (TileLayer) layers.get(layerIndex);
            renderer.add(layer, tileSize, layerIndex - 1);
        }
    }
}
