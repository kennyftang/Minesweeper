import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Minesweeper extends JFrame implements Runnable{
    private GameState gameState;
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
        //Define Images
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
        ImageIcon HIDDEN_PRESSED = new ImageIcon("img/phidden.png");
        //Define Segment Display
        ImageIcon SEG7 = new ImageIcon("img/SEG_7_SCALED.png");
        //Define GUI Components
		JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenuItem newItem = new JMenuItem("New");
        JMenuItem exitItem = new JMenuItem("Exit");
        JPanel gamePanel = new JPanel();
        JPanel displayPanel = new JPanel();
        JPanel mineDisplayPanel = new JPanel(){
            protected void paintComponent(Graphics g){
                g.drawImage(SEG7.getImage(), 0, 0, null);
            }
        };
        JPanel timeDisplayPanel = new JPanel();
        //Add displays to display panel
        displayPanel.setLayout(new BorderLayout());
        mineDisplayPanel.setLayout(new BoxLayout(mineDisplayPanel, BoxLayout.X_AXIS));
        timeDisplayPanel.setLayout(new BoxLayout(timeDisplayPanel, BoxLayout.X_AXIS));
        displayPanel.add(mineDisplayPanel, BorderLayout.WEST);
        displayPanel.add(timeDisplayPanel, BorderLayout.EAST);
        //DEBUG


        //Add display and game to GUI
        this.setLayout(new BorderLayout());
        this.add(gamePanel, BorderLayout.CENTER);
        this.add(displayPanel, BorderLayout.NORTH);
        //Game Menu
        menuBar.add(gameMenu);
        //Creates menu with items: New | Exit
        gameMenu.add(newItem);
        gameMenu.addSeparator();
        gameMenu.add(exitItem);
        //Add actions to menu items
        this.add(gameMenu);
        this.add(displayPanel);
        this.add(gamePanel);
        newItem.addActionListener((ActionEvent actionEvent) -> {
            try {
                DifficultyDialog difficultyDialog = new DifficultyDialog(this);
                difficultyDialog.setSize(500, 200);
                difficultyDialog.setVisible(true);
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        });

        //GUI Start


	}
    public GameState getGameState(){
        return gameState;
    }
	public void run(){ //Used for timer in the GUI

	}
}