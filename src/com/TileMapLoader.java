package com;

import java.util.ArrayList;

import com.query.Physics;
import com.query.Rect;

import com.rendering.Renderer;

import com.tilemap.CollisionLayer;
import com.tilemap.Layer;
import com.tilemap.TileLayer;
import com.tilemap.TileMap;
import com.tilemap.TileParser;

public final class TileMapLoader {

    private TileMap tileMap;

    private int tileSize;

    public TileMapLoader(String filePath, TileParser tileParser, int tileSize) {
        super();
        this.tileSize = tileSize;
        tileMap = new TileMap(filePath, tileParser, tileSize);
    }

    public Physics parseCollisionLayer() {
        double worldWidth = tileMap.numTilesX * tileSize;
        double worldHeight = tileMap.numTilesY * tileSize;
        Physics physics = new Physics(1 / 60.0d, worldWidth, worldHeight, 128);

        CollisionLayer collisionLayer = (CollisionLayer) tileMap.getLayer(0);
        int numRows = tileMap.numTilesY;
        int numCols = tileMap.numTilesX;

        /*
         * Naive Implementation: Loop through the collision layer and create a tileSize
         * by tileSize Rect on each of the collidable tiles.
         */

        int count = 0;
        // for (int row = 0; row < numRows; row++) {
        //     for (int col = 0; col < numCols; col++) {
        //         if (!collisionLayer.isCollidable(row, col)) {
        //             continue;
        //         }
        //         Rect wall = new Rect(col * tileSize, row * tileSize, tileSize, tileSize);
        //         wall.setAnchored(true);
        //         count++;
        //         physics.addCollider(wall);
        //     }
        // }

        // Better Implementation: Perform Greedy Mesh on the collisionLayer

        // First greedy pass along rows
        int[][] colWidths = new int[numRows][numCols];
        for (int row = 0; row < numRows; row++) {
            int startCol = 0;
            int endCol = 0;

            while (endCol < numCols) {
                if (!collisionLayer.isCollidable(row, startCol)) { // No collision mesh to generate here.
                    startCol++;
                    endCol = startCol;
                    continue;
                }

                if (!collisionLayer.isCollidable(row, endCol)) { // End of current collision mesh
                    colWidths[row][startCol] = endCol - startCol;
                    endCol++;
                    startCol = endCol;
                    continue;
                }

                endCol++;

                if (endCol == numCols) { // Last collision mesh in row
                    colWidths[row][startCol] = endCol - startCol;
                }
            }
        }

        // Second greedy pass along columns
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                int width = colWidths[row][col];
                if (width == 0) {
                    continue;
                }

                int endRow = row + 1;
                while (endRow < numRows && colWidths[endRow][col] == width) {
                    colWidths[endRow][col] = 0;
                    endRow++;

                }
                int height = endRow - row;
                count++;
                Rect wall = new Rect(col * tileSize, row * tileSize, width * tileSize, height * tileSize);
                wall.setAnchored(true);
                physics.addCollider(wall);
            }
        }

        System.out.println(count + " colliders created");

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
