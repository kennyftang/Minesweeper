import javax.swing.*;
import java.awt.*;

class Cell extends JLabel {
    //Cell Types
    static final int HIDDEN = 0;
    static final int BLANK = 1;
    static final int MINE = 2;
    static final int FLAG = 3;
    static final int ONE = 4;
    static final int TWO = 5;
    static final int THREE = 6;
    static final int FOUR = 7;
    static final int FIVE = 8;
    static final int SIX = 9;
    static final int SEVEN = 10;
    static final int EIGHT = 11;

    private final Point cellLocation;
    private int cellType;

    Cell(Point cellLocation){
        super();
        ImageIcon HIDDEN = new ImageIcon("img/hidden.png");
        super.setIcon(HIDDEN);
        this.cellLocation = cellLocation;
    }

    int getCellType() {
        return cellType;
    }

    public void setCellType(int cellType) {
        this.cellType = cellType;
    }

    public Point getCellLocation(){
        return cellLocation;
    }
}
