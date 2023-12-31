package com.tilemap;

// import java.awt.Color;
// import java.awt.Graphics;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

// import rendering.Camera;

public class TileMap {

    private TileParser tileParser;

    private ArrayList<Layer> layers;

    public int scale;

    public int numTilesX, numTilesY;

    public int[][][] serializedTileset;

    public TileMap(String fileName, TileParser tileParser, int scale) {
        this.tileParser = tileParser;
        this.scale = scale;
        loadMap(fileName);
    }

    public TileMap(TileParser tileParser, int scale, int numRows, int numCols) {
        this.tileParser = tileParser;
        this.numTilesX = numCols;
        this.numTilesY = numRows;
        this.layers = new ArrayList<>();
        this.layers.add(new CollisionLayer(numRows, numCols));
        this.layers.add(new TileLayer(numTilesY, numTilesX, tileParser));
        this.scale = scale;
    }

    private void loadLayer(Layer layer, BufferedReader input) throws IOException {
        for (int row = 0; row < numTilesY; row++) {
            String line = input.readLine();
            String[] codes = line.split(",");
            for (int col = 0; col < numTilesX; col++) {
                int code = tileParser.parseString(codes[col]);
                layer.setSerialized(row, col, code);
            }
        }
    }

    public void loadMap(String filePath) {
        File file = new File(filePath);

        try (BufferedReader input = new BufferedReader(new FileReader(file))) {
            int numLayers = Integer.parseInt(input.readLine()); // How many layers in the map?
            numTilesX = Integer.parseInt(input.readLine()); // How many columns in each layer?
            numTilesY = Integer.parseInt(input.readLine()); // How many rows in each layer?

            layers = new ArrayList<>(numLayers);
            layers.add(new CollisionLayer(numTilesY, numTilesX));
            loadLayer(layers.get(0), input);

            // start from 1 because we skip the collision layer
            for (int i = 1; i < numLayers; i++) {
                TileLayer layer = new TileLayer(numTilesY, numTilesX, tileParser);
                loadLayer(layer, input);
                layers.add(layer);
            }

            System.out.println("Map Layers: " + numLayers);
            System.out.println("Map Rows: " + numTilesY);
            System.out.println("Map Columns: " + numTilesX);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveLayer(Layer layer, BufferedWriter output) throws IOException {
        for (int row = 0; row < numTilesY; row++) {
            for (int col = 0; col < numTilesX; col++) {
                String code = tileParser.getString(layer.getSerialized(row, col));
                String separator = col > 0 ? "," : "";
                output.write(separator + code);
            }
            output.write("\n");
        }
    }

    public void saveMap(String filePath) {
        File file = new File(filePath);

        try (BufferedWriter output = new BufferedWriter(new FileWriter(file))) {
            output.write(layers.size() + "\n");
            output.write(numTilesX + "\n");
            output.write(numTilesY + "\n");

            for (Layer layer : layers) {
                saveLayer(layer, output);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TileLayer addLayer() {
        TileLayer layer = new TileLayer(numTilesY, numTilesX, tileParser);
        layers.add(layer);
        return layer;
    }

    public void removeLayer(int index) {
        if (index == 0) {
            System.out.println("Collision layer cannot be removed");
            return;
        }

        layers.remove(index);
    }

    public Layer getLayer(int layerIndex) {
        return layers.get(layerIndex);
    }

    public ArrayList<Layer> getLayers() {
        return layers;
    }

}
