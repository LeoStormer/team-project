package com.tilemap;

// import java.awt.Graphics;

public class CollisionLayer extends Layer {

    private boolean[][] collisionData;

    public CollisionLayer(int numRows, int numCols) {
        super(numRows, numCols);
        collisionData = new boolean[numRows][numCols];
    }

    public boolean isCollidable(int row, int col) {
        return collisionData[row][col];
    }

    public void setCollisionData(int row, int col, boolean isCollidable) {
        collisionData[row][col] = isCollidable;
    }

    @Override
    public void update(int row, int col, int code) {
        boolean isCollidable = (code & 1) == 1; // check if first bit is a 1
        setCollisionData(row, col, isCollidable);
    }

}
