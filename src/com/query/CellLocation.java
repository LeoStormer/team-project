package com.query;

public class CellLocation {

    public int x, y;

    public CellLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(CellLocation other) {
        return x == other.x && y == other.y;
    }

}
