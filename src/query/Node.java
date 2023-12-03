package query;

public class Node {
    public Collider collider;
    protected CellLocation startCellLocation;
    protected CellLocation endCellLocation;

    public Node(Collider collider, CellLocation startLocation, CellLocation endLocation) {
        this.collider = collider;
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
