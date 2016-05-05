import javax.swing.*;
import java.awt.*;

public class Cell extends JLabel {
    private Point cellLocation;
    private int cellType;
    public Cell(Point cellLocation){
        super();
        ImageIcon HIDDEN = new ImageIcon("img/hidden.png");
        super.setIcon(HIDDEN);
        this.cellLocation = cellLocation;
    }

    public int getCellType() {
        return cellType;
    }

    public void setCellType(int cellType) {
        this.cellType = cellType;
    }

    public Point getCellLocation(){
        return cellLocation;
    }
}
