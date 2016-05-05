import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Minesweeper extends JFrame implements Runnable{
    private GameState gameState;
    private JPanel gamePanel;
    //Define Minesweeper Icons
    ImageIcon ONE_ADJ = new ImageIcon("img/1.png");
    ImageIcon TWO_ADJ = new ImageIcon("img/2.png");
    ImageIcon THREE_ADJ = new ImageIcon("img/3.png");
    ImageIcon FOUR_ADJ = new ImageIcon("img/4.png");
    ImageIcon FIVE_ADJ = new ImageIcon("img/5.png");
    ImageIcon SIX_ADJ = new ImageIcon("img/6.png");
    ImageIcon SEVEN_ADJ = new ImageIcon("img/7.png");
    ImageIcon EIGHT_ADJ = new ImageIcon("img/8.png");
    ImageIcon BLANK = new ImageIcon("img/blank.png");
    ImageIcon FLAG = new ImageIcon("img/flag.png");
    ImageIcon MINE = new ImageIcon("img/mine.png");
    ImageIcon HIDDEN = new ImageIcon("img/hidden.png");
    ImageIcon HIDDENNB = new ImageIcon("img/hiddennb.png");
    ImageIcon HIDDEN_PRESSED = new ImageIcon("img/phidden.png");
    //Define Segment Icons
    ImageIcon SEG0 = new ImageIcon("img/SEG_0.png");
    ImageIcon SEG1 = new ImageIcon("img/SEG_1.png");
    ImageIcon SEG2 = new ImageIcon("img/SEG_2.png");
    ImageIcon SEG3 = new ImageIcon("img/SEG_3.png");
    ImageIcon SEG4 = new ImageIcon("img/SEG_4.png");
    ImageIcon SEG5 = new ImageIcon("img/SEG_5.png");
    ImageIcon SEG6 = new ImageIcon("img/SEG_6.png");
    ImageIcon SEG7 = new ImageIcon("img/SEG_7.png");
    ImageIcon SEG8 = new ImageIcon("img/SEG_8.png");
    ImageIcon SEG9 = new ImageIcon("img/SEG_9.png");
    //Runner
	public static void main(String[] args){
		Minesweeper client = new Minesweeper();
        client.setSize(400, 500);
        client.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        client.setVisible(true);
	}

    //Game Logic
	public Minesweeper(){
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
        mineDisplayPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5) ,BorderFactory.createBevelBorder(BevelBorder.RAISED)));
        timeDisplayPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5) ,BorderFactory.createBevelBorder(BevelBorder.RAISED)));

        JLabel mineLabel1 = new JLabel(SEG0);
        JLabel mineLabel2 = new JLabel(SEG1);
        JLabel mineLabel3 = new JLabel(SEG0);
        mineDisplayPanel.add(mineLabel1);
        mineDisplayPanel.add(mineLabel2);
        mineDisplayPanel.add(mineLabel3);

        JLabel timeLabel1 = new JLabel(SEG0);
        JLabel timeLabel2 = new JLabel(SEG0);
        JLabel timeLabel3 = new JLabel(SEG0);
        timeDisplayPanel.add(timeLabel1);
        timeDisplayPanel.add(timeLabel2);
        timeDisplayPanel.add(timeLabel3);

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
        exitItem.addActionListener((ActionEvent actionEvent) -> {
            System.exit(0);
        });

        //Game Start
        gamePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));
        updateGame();
	}
    public void discoverAround(Cell origin){

    }
    public void updateGame(){
        MouseListener cellMouseListener = new MouseListener() {
            private boolean inCell = false;
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {
                ((Cell)e.getSource()).setIcon(HIDDEN_PRESSED);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(inCell){

                } else
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
        Cell cell1 = new Cell(new Point(0,0));
        cell1.addMouseListener(cellMouseListener);
        gamePanel.add(cell1);
    }
    public GameState getGameState(){
        return gameState;
    }
	public void run(){ //Used for timer in the GUI

	}
}