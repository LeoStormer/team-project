package com.tilemap;

import com.rendering.Tile;

public class TileLayer extends Layer {

    private Tile[][] tileGrid;

    private TileParser tileParser;

    public TileLayer(int numRows, int numCols, TileParser tileParser) {
        super(numRows, numCols);
        this.tileParser = tileParser;
        tileGrid = new Tile[numRows][numCols];
    }

    public Tile getTile(int row, int col) {
        return tileGrid[row][col];
    }

    public void setTile(int row, int col, Tile tile) {
        tileGrid[row][col] = tile;
    }

    @Override
    public void update(int row, int col, int code) {
        Tile tile = tileParser.getTile(code);
        setTile(row, col, tile);
    }

}
