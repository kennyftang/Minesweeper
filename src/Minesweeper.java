import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Minesweeper extends JFrame{
    private GameState gameState;
    private JPanel gamePanel;
    private JLabel mineLabel1;
    private JLabel mineLabel2;
    private JLabel mineLabel3;
    private JLabel timerLabel1;
    private JLabel timerLabel2;
    private JLabel timerLabel3;
    //Define Minesweeper Icons
    private final ImageIcon ONE_ADJ = new ImageIcon("img/1.png");
    private final ImageIcon TWO_ADJ = new ImageIcon("img/2.png");
    private final ImageIcon THREE_ADJ = new ImageIcon("img/3.png");
    private final ImageIcon FOUR_ADJ = new ImageIcon("img/4.png");
    private final ImageIcon FIVE_ADJ = new ImageIcon("img/5.png");
    private final ImageIcon SIX_ADJ = new ImageIcon("img/6.png");
    private final ImageIcon SEVEN_ADJ = new ImageIcon("img/7.png");
    private final ImageIcon EIGHT_ADJ = new ImageIcon("img/8.png");
    private final ImageIcon BLANK = new ImageIcon("img/blank.png");
    private final ImageIcon FLAG = new ImageIcon("img/flag.png");
    private final ImageIcon MINE = new ImageIcon("img/mine.png");
    private final ImageIcon MINE_LOSE = new ImageIcon("img/minelose.png");
    private final ImageIcon HIDDEN = new ImageIcon("img/hidden.png");
    private final ImageIcon HIDDEN_PRESSED = new ImageIcon("img/phidden.png");
    //Define Segment Icons
    private final ImageIcon SEG0 = new ImageIcon("img/SEG_0.png");
    private final ImageIcon SEG1 = new ImageIcon("img/SEG_1.png");
    private final ImageIcon SEG2 = new ImageIcon("img/SEG_2.png");
    private final ImageIcon SEG3 = new ImageIcon("img/SEG_3.png");
    private final ImageIcon SEG4 = new ImageIcon("img/SEG_4.png");
    private final ImageIcon SEG5 = new ImageIcon("img/SEG_5.png");
    private final ImageIcon SEG6 = new ImageIcon("img/SEG_6.png");
    private final ImageIcon SEG7 = new ImageIcon("img/SEG_7.png");
    private final ImageIcon SEG8 = new ImageIcon("img/SEG_8.png");
    private final ImageIcon SEG9 = new ImageIcon("img/SEG_9.png");
    //Instance Variables
    private int timerTime;
    private Timer gameTimer;
    private Set<Cell> mineCells;

    //Runner
	public static void main(String[] args){
		Minesweeper client = new Minesweeper();
        client.setSize(400, 500);
        client.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        client.setVisible(true);
        client.pack();
	}

    //Game Logic
	private Minesweeper(){
        gameState = new GameState();
        //Define GUI Components
		JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenuItem newItem = new JMenuItem("New");
        JMenuItem exitItem = new JMenuItem("Exit");
        gamePanel = new JPanel();
        JPanel displayPanel = new JPanel();
        JPanel mineDisplayPanel = new JPanel();
        JPanel timeDisplayPanel = new JPanel();
        //Define and Add JLabels for time and mines
        //Makes the time and mine display look nicer :)
        mineDisplayPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5) ,BorderFactory.createBevelBorder(BevelBorder.RAISED)));
        timeDisplayPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5) ,BorderFactory.createBevelBorder(BevelBorder.RAISED)));
        //Starts mines at 10 (beginner level)
        mineLabel1 = new JLabel(SEG0);
        mineLabel2 = new JLabel(SEG1);
        mineLabel3 = new JLabel(SEG0);
        mineDisplayPanel.add(mineLabel1);
        mineDisplayPanel.add(mineLabel2);
        mineDisplayPanel.add(mineLabel3);
        //Starts timer at 000 sec
        timerLabel1 = new JLabel(SEG0);
        timerLabel2 = new JLabel(SEG0);
        timerLabel3 = new JLabel(SEG0);
        timeDisplayPanel.add(timerLabel1);
        timeDisplayPanel.add(timerLabel2);
        timeDisplayPanel.add(timerLabel3);
        //Add displays to display panel
        displayPanel.setLayout(new BorderLayout());
        mineDisplayPanel.setLayout(new BoxLayout(mineDisplayPanel, BoxLayout.X_AXIS));
        timeDisplayPanel.setLayout(new BoxLayout(timeDisplayPanel, BoxLayout.X_AXIS));
        displayPanel.add(mineDisplayPanel, BorderLayout.WEST);
        displayPanel.add(timeDisplayPanel, BorderLayout.EAST);
        //Add display and game to GUI
        this.setLayout(new BorderLayout());
        this.setJMenuBar(menuBar);
        this.add(gamePanel, BorderLayout.CENTER);
        this.add(displayPanel, BorderLayout.NORTH);
        //Game Menu
        menuBar.add(gameMenu);
        //Creates menu with items: New | Exit
        gameMenu.add(newItem);
        gameMenu.addSeparator();
        gameMenu.add(exitItem);
        //Add actions to menu items
        newItem.addActionListener((ActionEvent actionEvent) -> {
            DifficultyDialog difficultyDialog = new DifficultyDialog(this);
            difficultyDialog.setSize(400, 100);
            difficultyDialog.pack();
            difficultyDialog.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            difficultyDialog.setVisible(true);
        });
        exitItem.addActionListener((ActionEvent actionEvent) -> System.exit(0));

        //Game Start
        gamePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));
        timerTime = 0;
        gameTimer = new Timer(1000, (ActionEvent actionEvent) -> incTime());
        initializeMap();

    }
    private boolean discoverAround(Cell origin, Set<Cell> visited, int minesAdj){
        Cell[][] map = gameState.getMap();
        int x = map.length - 1;
        int y = map[0].length - 1;
        int cellX = origin.getCellLocation().x;
        int cellY = origin.getCellLocation().y;
        int minesAdjacent = 0;
        if(origin.getCellType() == Cell.MINE) {
            return true;
        }
        if(!visited.add(origin))
            return false;
        if(minesAdj > 0)
            return false;
        if(cellX > 0)
            minesAdjacent = discoverAround(map[cellY][cellX - 1], visited, minesAdjacent) ? minesAdjacent + 1 : minesAdjacent;
        if(cellY > 0)
            minesAdjacent = discoverAround(map[cellY - 1][cellX], visited, minesAdjacent) ? minesAdjacent + 1 : minesAdjacent;
        if(cellX < x)
            minesAdjacent = discoverAround(map[cellY][cellX + 1], visited, minesAdjacent) ? minesAdjacent + 1 : minesAdjacent;
        if(cellY < y)
            minesAdjacent = discoverAround(map[cellY + 1][cellX], visited, minesAdjacent) ? minesAdjacent + 1 : minesAdjacent;
        if(cellX < x && cellY < y)
            minesAdjacent = discoverAround(map[cellY + 1][cellX + 1], visited, minesAdjacent) ? minesAdjacent + 1 : minesAdjacent;
        if(cellX > 0 && cellY < y)
            minesAdjacent = discoverAround(map[cellY + 1][cellX - 1], visited, minesAdjacent) ? minesAdjacent + 1 : minesAdjacent;
        if(cellX < x && cellY > 0)
            minesAdjacent = discoverAround(map[cellY - 1][cellX + 1], visited, minesAdjacent) ? minesAdjacent + 1 : minesAdjacent;
        if(cellX > 0 && cellY > 0)
            minesAdjacent = discoverAround(map[cellY - 1][cellX - 1], visited, minesAdjacent) ? minesAdjacent + 1 : minesAdjacent;
        origin.setIcon(adjIconFromInt(minesAdjacent));
        return false;
    }
    private void initializeMap(){
        Cell[][] map = gameState.getMap();
        mineCells = new HashSet<>();
        for(int i = 0; i < map.length; i++){
            JPanel cellRow = new JPanel();
            cellRow.setLayout(new BoxLayout(cellRow, BoxLayout.X_AXIS));
            for(int j = 0; j < map[i].length; j++){
                map[i][j] = new Cell(new Point(j, i));
                MouseListener cellMouseListener = new MouseListener() {
                    private boolean inCell = false;
                    private boolean flagged = false;
                    public void mouseClicked(MouseEvent e) {}
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if(e.getButton() == MouseEvent.BUTTON3) {
                            flagged = !flagged;
                            if(flagged)
                                ((Cell)e.getSource()).setIcon(FLAG);
                            else
                                ((Cell)e.getSource()).setIcon(HIDDEN);
                        }
                        else if(!flagged)
                            ((Cell)e.getSource()).setIcon(HIDDEN_PRESSED);
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if(!gameState.isGameRunning())
                            startGame();
                        if(inCell && !flagged){
                            checkCell(((Cell)e.getSource()));
                        } else if(!flagged)
                            ((Cell)e.getSource()).setIcon(HIDDEN);
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        inCell = true;
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        inCell = false;
                    }
                };
                map[i][j].addMouseListener(cellMouseListener);
                cellRow.add(map[i][j]);
            }
            gamePanel.add(cellRow);
        }

        mineLabel1.setIcon(timerIconFromInt(timerTime/100));
        mineLabel2.setIcon(timerIconFromInt((timerTime%100)/10));
        mineLabel3.setIcon(timerIconFromInt(timerTime%10));
    }
    private void checkCell(Cell cell){
        if(cell.getCellType() == Cell.MINE){
            for(Cell mines : mineCells){
                mines.setIcon(MINE);
            }
            cell.setIcon(MINE_LOSE);
        } else if(cell.getCellType() == Cell.HIDDEN){
            discoverAround(cell, new HashSet<Cell>(), 0);
        }
    }
    public void startGame(){
        if(!gameTimer.isRunning())
            gameTimer.start();
        gameState.setGameRunning(true);
        ArrayList<Integer> mineRandomSpread = new ArrayList<>();
        for(int i = 0; i < gameState.getMap().length * gameState.getMap()[0].length; i++)
            mineRandomSpread.add(i);
        Collections.shuffle(mineRandomSpread);
        for(int i = 0; i < gameState.getMines(); i++) {
            int mine = mineRandomSpread.remove((int)(Math.random() * mineRandomSpread.size()));
            gameState.getMap()[mine / gameState.getMap().length][mine % gameState.getMap().length].setCellType(Cell.MINE);
        }
    }
    GameState getGameState(){
        return gameState;
    }
    private void incTime(){
        timerTime++;
        timerLabel1.setIcon(timerIconFromInt(timerTime/100));
        timerLabel2.setIcon(timerIconFromInt((timerTime%100)/10));
        timerLabel3.setIcon(timerIconFromInt(timerTime%10));
    }
    private Icon timerIconFromInt(int num){
        switch(num){
            case 0:
                return SEG0;
            case 1:
                return SEG1;
            case 2:
                return SEG2;
            case 3:
                return SEG3;
            case 4:
                return SEG4;
            case 5:
                return SEG5;
            case 6:
                return SEG6;
            case 7:
                return SEG7;
            case 8:
                return SEG8;
            case 9:
                return SEG9;
            default:
                return null;
        }
    }
    private Icon adjIconFromInt(int num){
        switch (num) {
            case 0:
                return BLANK;
            case 1:
                return ONE_ADJ;
            case 2:
                return TWO_ADJ;
            case 3:
                return THREE_ADJ;
            case 4:
                return FOUR_ADJ;
            case 5:
                return FIVE_ADJ;
            case 6:
                return SIX_ADJ;
            case 7:
                return SEVEN_ADJ;
            case 8:
                return EIGHT_ADJ;
            default:
                return null;
        }
    }
}