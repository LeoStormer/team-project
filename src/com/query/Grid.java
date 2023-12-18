package com.query;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.HashSet;

import com.rendering.Camera;
import com.rendering.Drawable;
import com.util.EMath;

public class Grid implements Drawable {
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

    public void add(Collider collider) {
        if (colliderMap.containsKey(collider)) {
            update(collider);
            return;
        }

        Vector2 rectMin = collider.getPosition();
        Vector2 rectMax = rectMin.add(collider.getSize());
        
        CellLocation startCell = getCellLocation(rectMin);
        CellLocation endCell = getCellLocation(rectMax);

        Node node = new Node(collider, startCell, endCell);
        colliderMap.put(collider, node);

        addToCells(collider, startCell, endCell);
    }

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

    public void remove(Collider collider) {
        Node node = colliderMap.remove(collider);
        removefromCells(collider, node.startCellLocation, node.endCellLocation);
    }

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

    public CellLocation getCellLocation(int px, int py) {
        int offsetX = px - x;
        int offsetY = py - y;
        int cellX = EMath.clamp(Math.floorDiv(offsetX, cellSize), 0, this.numCellsX - 1);
        int cellY = EMath.clamp(Math.floorDiv(offsetY, cellSize), 0, this.numCellsY - 1);
        
        return new CellLocation(cellX, cellY);
    }

    public CellLocation getCellLocation(Vector2 position) {
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
