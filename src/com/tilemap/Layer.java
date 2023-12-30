package com.tilemap;

// import java.awt.Graphics;

public abstract class Layer {

    private int[][] serializedGrid;

    public boolean contentVisible;

    public final int numRows, numCols;

    public Layer(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        contentVisible = true;
        serializedGrid = new int[numRows][numCols];
    }

    public int getSerialized(int row, int col) {
        return serializedGrid[row][col];
    }

    public void setSerialized(int row, int col, int code) {
        serializedGrid[row][col] = code;
        update(row, col, code);
    }

    public boolean isContentVisible() {
        return contentVisible;
    }

    public void setContentVisible(boolean contentVisible) {
        this.contentVisible = contentVisible;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public abstract void update(int row, int col, int code);

}
