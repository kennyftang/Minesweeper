import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

class Cell extends JLabel {

    //Cell Types
    static final int BLANK = 0;
    static final int ONE = 1;
    static final int TWO = 2;
    static final int THREE = 3;
    static final int FOUR = 4;
    static final int FIVE = 5;
    static final int SIX = 6;
    static final int SEVEN = 7;
    static final int EIGHT = 8;
    static final int MINE = 10;
    static final int HIDDEN = 11;
    static final int FLAG = 12;

    //Location of the cell in respect to the map
    private final Point cellLocation;
    //This is the visible cell type.
    private int cellType;
    //This is the hidden cell type that may or may not be visible.
    private int hiddenType;

    Cell(Point cellLocation) {
        super();
        ImageIcon HIDDEN = new ImageIcon("img/hidden.png");
        super.setIcon(HIDDEN);
        this.cellLocation = cellLocation;
        cellType = Cell.HIDDEN;
        hiddenType = -1;
    }

    int getHiddenType() {
        return hiddenType;
    }

    void setHiddenType(int hiddenType) {
        this.hiddenType = hiddenType;
    }

    int getCellType() {
        return cellType;
    }

    void setCellType(int cellType) {
        this.cellType = cellType;
    }

    Point getCellLocation() {
        return cellLocation;
    }
    //Returns a list of all the adjacent cells from the given map and the current cell's location
    LinkedList<Cell> getAdjacentCells(Cell[][] map) {
        LinkedList<Cell> adjCells = new LinkedList<>();
        int y = cellLocation.y;
        int x = cellLocation.x;
        if (y > 0)
            adjCells.add(map[y - 1][x]);
        if (x > 0)
            adjCells.add(map[y][x - 1]);
        if (y < map.length - 1)
            adjCells.add(map[y + 1][x]);
        if (x < map[y].length - 1)
            adjCells.add(map[y][x + 1]);
        if (y > 0 && x < map[0].length - 1)
            adjCells.add(map[y - 1][x + 1]);
        if (x > 0 && y < map.length - 1)
            adjCells.add(map[y + 1][x - 1]);
        if (y > 0 && x > 0)
            adjCells.add(map[y - 1][x - 1]);
        if (y < map.length - 1 && x < map[0].length - 1)
            adjCells.add(map[y + 1][x + 1]);
        return adjCells;
    }
}
