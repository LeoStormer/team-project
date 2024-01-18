package com.query;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.HashSet;

import com.rendering.Camera;

import com.util.EMath;

/**
 * This class partitions a rectangular portion of 2d space into equally sized
 * portions for the purpose of reducing the time complexity of spatial queries.
 */
public class Grid {

    /**
     * A class that represents a portion of a space.
     */
    private class Cell {

        private HashSet<Collider> colliders;

        public Cell() {
            colliders = new HashSet<>();
        }

        public void add(Collider c) {
            colliders.add(c);
        }

        public void remove(Collider c) {
            colliders.remove(c);
        }

        public void collect(HashSet<Collider> s) {
            s.addAll(colliders);
        }

    }

    private class CellLocation {

        public int x, y;

        public CellLocation(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public boolean equals(CellLocation other) {
            return x == other.x && y == other.y;
        }

    }

    private class Node {

        public CellLocation startCellLocation;

        public CellLocation endCellLocation;

        public Node(CellLocation startLocation, CellLocation endLocation) {
            this.startCellLocation = startLocation;
            this.endCellLocation = endLocation;
        }

        public void setStartCellLocation(CellLocation startCellLocation) {
            this.startCellLocation = startCellLocation;
        }

        public void setEndCellLocation(CellLocation endCellLocation) {
            this.endCellLocation = endCellLocation;
        }

    }

    private int x, y;

    private int numCellsX, numCellsY;

    private int cellSize;

    private HashMap<Collider, Node> colliderMap;

    private Cell[][] cells;

    public Grid(int x, int y, int width, int height, int cellSize) {
        this.numCellsX = width / cellSize;
        this.numCellsY = height / cellSize;
        this.cellSize = cellSize;
        this.colliderMap = new HashMap<>();
        this.x = x;
        this.y = y;

        cells = new Cell[this.numCellsX][this.numCellsY];
        for (int i = 0; i < this.numCellsX; i++) {
            for (int j = 0; j < this.numCellsY; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    private void addToCells(Collider collider, CellLocation start, CellLocation end) {
        for (int i = start.x; i < end.x + 1; i++) {
            for (int j = start.y; j < end.y + 1; j++) {
                Cell cell = cells[i][j];
                cell.add(collider);
            }
        }
    }

    private void removefromCells(Collider collider, CellLocation start, CellLocation end) {
        for (int i = start.x; i < end.x + 1; i++) {
            for (int j = start.y; j < end.y + 1; j++) {
                Cell cell = cells[i][j];
                cell.remove(collider);
            }
        }
    }

    /**
     * Add a collider to the grid.
     * 
     * @param collider
     */
    public void add(Collider collider) {
        if (colliderMap.containsKey(collider)) {
            update(collider);
            return;
        }

        Vector2 rectMin = collider.getPosition();
        Vector2 rectMax = rectMin.add(collider.getSize());

        CellLocation startCell = getCellLocation(rectMin);
        CellLocation endCell = getCellLocation(rectMax);

        Node node = new Node(startCell, endCell);
        colliderMap.put(collider, node);

        addToCells(collider, startCell, endCell);
    }

    /**
     * Update the position of a collider within the grid.
     * 
     * @param collider
     */
    public void update(Collider collider) {
        Node node = colliderMap.get(collider);

        Vector2 rectMin = collider.getPosition();
        Vector2 rectMax = rectMin.add(collider.getSize());

        CellLocation startCell = getCellLocation(rectMin);
        CellLocation endCell = getCellLocation(rectMax);
        if (node.startCellLocation.equals(startCell) && node.endCellLocation.equals(endCell)) {
            return;
        }

        removefromCells(collider, node.startCellLocation, node.endCellLocation);
        node.setStartCellLocation(startCell);
        node.setEndCellLocation(endCell);
        addToCells(collider, startCell, endCell);
    }

    /**
     * Remove a collider from the grid.
     * 
     * @param collider
     */
    public void remove(Collider collider) {
        Node node = colliderMap.remove(collider);
        removefromCells(collider, node.startCellLocation, node.endCellLocation);
    }

    /**
     * @param rect
     * @return An array of all {@link Collider colliders} that are in the same
     *         portions of space the rect is occupying
     */
    public Collider[] query(Rect rect) {
        Vector2 rectMin = rect.getPosition();

        CellLocation startCellLocation = getCellLocation(rectMin);
        CellLocation endCellLocation = getCellLocation(rectMin.add(rect.getSize()));

        HashSet<Collider> found = new HashSet<>();
        for (int i = startCellLocation.x; i < endCellLocation.x + 1; i++) {
            for (int j = startCellLocation.y; j < endCellLocation.y + 1; j++) {
                Cell cell = cells[i][j];
                cell.collect(found);
            }
        }

        return found.toArray(new Collider[found.size()]);
    }

    private CellLocation getCellLocation(int px, int py) {
        int offsetX = px - x;
        int offsetY = py - y;
        int cellX = EMath.clamp(Math.floorDiv(offsetX, cellSize), 0, this.numCellsX - 1);
        int cellY = EMath.clamp(Math.floorDiv(offsetY, cellSize), 0, this.numCellsY - 1);

        return new CellLocation(cellX, cellY);
    }

    private CellLocation getCellLocation(Vector2 position) {
        return getCellLocation((int) position.getX(), (int) position.getY());
    }

    public void draw(Graphics g, Camera cam) {
        Vector2 camMin = cam.getPosition();
        Vector2 camMax = camMin.add(cam.getSize());
        CellLocation minCell = getCellLocation(camMin);
        CellLocation maxCell = getCellLocation(camMax);
        int camLeft = (int) camMin.getX();
        int camTop = (int) camMin.getY();

        for (int i = minCell.x; i < maxCell.x + 1; i++) {
            int screenX = x + i * cellSize - camLeft;
            for (int j = minCell.y; j < maxCell.y + 1; j++) {
                int screenY = y + j * cellSize - camTop;
                g.drawRect(screenX, screenY, this.cellSize, this.cellSize);
            }
        }
    }

}
