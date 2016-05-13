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

public class Minesweeper extends JFrame {
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
    private final GameState gameState;
    private final JLabel mineLabel1;
    private final JLabel mineLabel2;
    private final JLabel mineLabel3;
    private final JLabel timerLabel1;
    private final JLabel timerLabel2;
    private final JLabel timerLabel3;
    //Instance Variables
    private int mineCount;
    private int timerTime;
    private final Timer gameTimer;
    private Set<Cell> mineCells;
    private JPanel oldGamePanel;

    //Game Logic
    private Minesweeper() {
        gameState = new GameState(this);
        //Define GUI Components
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenuItem newItem = new JMenuItem("New");
        JMenuItem exitItem = new JMenuItem("Exit");
        JPanel displayPanel = new JPanel();
        JPanel mineDisplayPanel = new JPanel();
        JPanel timeDisplayPanel = new JPanel();
        //Define and Add JLabels for time and mines
        //Makes the time and mine display look nicer :)
        mineDisplayPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), BorderFactory.createBevelBorder(BevelBorder.RAISED)));
        timeDisplayPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), BorderFactory.createBevelBorder(BevelBorder.RAISED)));
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
        timerTime = 0;
        gameTimer = new Timer(1000, (ActionEvent actionEvent) -> incTime(1));
        initializeMap();

    }

    //Runner
    public static void main(String[] args) {
        Minesweeper client = new Minesweeper();
        client.setSize(400, 500);
        client.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        client.setVisible(true);
        client.pack();
    }

    private void mapMap() {
        Cell[][] map = gameState.getMap();
        for (Cell[] cellRow : map) {
            for (Cell curCell : cellRow) {
                if (curCell.getHiddenType() == Cell.MINE)
                    continue;
                int minesAdjacent = 0;
                for (Cell adj : curCell.getAdjacentCells(map)) {
                    if (adj.getHiddenType() == Cell.MINE)
                        minesAdjacent++;
                }
                curCell.setHiddenType(minesAdjacent);
            }
        }
    }

    /*
    This method is run in order to prepare the gamePanel for a new game.
    The current gamePanel will be replaced with a new gamePanel every time it is called.

     */
    void initializeMap() {
        JPanel gamePanel = new JPanel();
        gamePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));
        Cell[][] map = gameState.getMap();
        mineCells = new HashSet<>();
        for (int i = 0; i < map.length; i++) {
            //Instantiates a new row for the game panel with a box layout so adding will add cells horizontally.
            JPanel cellRow = new JPanel();
            cellRow.setLayout(new BoxLayout(cellRow, BoxLayout.X_AXIS));
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = new Cell(new Point(j, i));
                MouseListener cellMouseListener = new MouseListener() {
                    private boolean inCell = false;
                    private boolean flagged = false;
                    private boolean skipCheck = false; // if right click

                    public void mouseClicked(MouseEvent e) {}

                    @Override
                    public void mousePressed(MouseEvent e) {
                        //If right click, cell is hidden or a flag
                        if (e.getButton() == MouseEvent.BUTTON3 && (((Cell)e.getSource()).getCellType() == Cell.HIDDEN || ((Cell)e.getSource()).getCellType() == Cell.FLAG)) {
                            if ((flagged = !flagged)) {
                                incMines(-1);
                                ((Cell)e.getSource()).setIcon(FLAG);
                                ((Cell)e.getSource()).setCellType(Cell.FLAG);
                            } else {
                                skipCheck = true;
                                incMines(1);
                                ((Cell)e.getSource()).setIcon(HIDDEN);
                                ((Cell)e.getSource()).setCellType(Cell.HIDDEN);
                            }
                        } else if (!flagged && ((Cell)e.getSource()).getCellType() == Cell.HIDDEN) {
                            skipCheck = false;
                            ((Cell)e.getSource()).setIcon(HIDDEN_PRESSED);
                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if (!gameState.isGameRunning())
                            startGame(((Cell)e.getSource()));
                        if (inCell && !flagged & !skipCheck)
                            checkCell(((Cell)e.getSource()));
                        checkWin();
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
                if (gameTimer != null) {
                    gameTimer.stop();
                    timerTime = 0;
                    incTime(0);
                }
            }
            gamePanel.add(cellRow);
        }
        mineCount = gameState.getMines();
        incMines(0);
        if (oldGamePanel != null)
            this.remove(oldGamePanel);
        oldGamePanel = gamePanel;
        this.add(gamePanel, BorderLayout.CENTER);
        this.pack();
    }

    private void revealCell(Cell cell) {
        if (adjIconFromInt(cell.getHiddenType()) != null) {
            cell.setIcon(adjIconFromInt(cell.getHiddenType()));
            cell.setCellType(cell.getHiddenType());
        }
    }

    //TODO Make a discover method
    private void checkCell(Cell cell) {
        if (cell.getHiddenType() == Cell.MINE) {
            //TODO Lose Game Implementation
            for (Cell mines : mineCells) {
                mines.setIcon(MINE);
            }
            cell.setIcon(MINE_LOSE);
            Cell[][] map = gameState.getMap();
            for(Cell[] cellRow : map){
                for(Cell loseCells : cellRow){
                    loseCells.removeMouseListener(loseCells.getMouseListeners()[0]);
                }
            }
            gameTimer.stop();
        } else if (cell.getCellType() == Cell.HIDDEN) {
            //TODO Add working discover method here
            for (Cell[] cellRow : gameState.getMap())
                for (Cell curCell : cellRow)
                    revealCell(curCell); //Temporary until discover method is implemented.
        }
    }

    private void startGame(Cell startCell) {
        if (!gameTimer.isRunning())
            gameTimer.start();
        Cell[][] map = gameState.getMap();
        gameState.setGameRunning(true);
        ArrayList<Integer> mineRandomSpread = new ArrayList<>();
        for (int i = 0; i < map.length * map[0].length; i++)
            mineRandomSpread.add(i);
        for(Cell adj : startCell.getAdjacentCells(map)){
            mineRandomSpread.remove((Integer)(adj.getCellLocation().y * map[0].length + adj.getCellLocation().x));
        }
        mineRandomSpread.remove((Integer)(startCell.getCellLocation().y * map[0].length + startCell.getCellLocation().x));
        Collections.shuffle(mineRandomSpread);
        for (int i = 0; i < gameState.getMines(); i++) {
            int mine = mineRandomSpread.remove((int)(Math.random() * mineRandomSpread.size()));
            Cell randCell = map[mine / map[0].length][mine % map[0].length];
            randCell.setHiddenType(Cell.MINE);
            mineCells.add(randCell);
        }
        mapMap();
    }

    GameState getGameState() {
        return gameState;
    }

    private void checkWin(){
        for(Cell[] cellRow : gameState.getMap())
            for(Cell cell : cellRow)
                if((cell.getCellType() == Cell.HIDDEN || cell.getCellType() == Cell.FLAG) && cell.getHiddenType() != Cell.MINE)
                    return;
        //TODO Add win stuff
    }

    private void incTime(int amt) {
        timerTime += amt;
        timerLabel1.setIcon(timerIconFromInt(timerTime / 100));
        timerLabel2.setIcon(timerIconFromInt((timerTime % 100) / 10));
        timerLabel3.setIcon(timerIconFromInt(timerTime % 10));
    }

    private void incMines(int amt) {
        mineCount += amt;
        if(mineCount < 0)
            mineCount = 0;
        mineLabel1.setIcon(timerIconFromInt(mineCount / 100));
        mineLabel2.setIcon(timerIconFromInt((mineCount % 100) / 10));
        mineLabel3.setIcon(timerIconFromInt(mineCount % 10));
    }

    private Icon timerIconFromInt(int num) {
        switch (num) {
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

    private Icon adjIconFromInt(int num) {
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