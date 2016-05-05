import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Minesweeper extends JFrame implements Runnable{
    private int[][] map;
    private int numMines;

    //Runner
	public static void main(String[] args){
		Minesweeper client = new Minesweeper();
        client.setSize(400, 500);
        client.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        client.setVisible(true);
	}

    //Game Logic
	public Minesweeper(){
        map = new int[8][8];

        //Define GUI Components
		JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenuItem newItem = new JMenuItem("New");
        JMenuItem exitItem = new JMenuItem("Exit");
        //Game Menu
        menuBar.add(gameMenu);
        //Creates menu with items: New | Exit
        gameMenu.add(newItem);
        gameMenu.addSeparator();
        gameMenu.add(exitItem);
        //Add actions to menu items
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


	}
    public void setMapSize(int x, int y, int mines){
        map = new int[x][y];
        numMines = mines;
    }
    //Difficulty Selector
    private class DifficultyDialog extends JFrame{
        int x, y, mines;
        public DifficultyDialog(Minesweeper client){
            JButton beginnerButton = new JButton("Beginner");
            JButton intermediateButton = new JButton("Intermediate");
            JButton experButton = new JButton("Expert");
            JButton customButton = new JButton("Custom");
            this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            this.add(beginnerButton);
            this.add(intermediateButton);
            this.add(experButton);
            this.add(customButton);
            beginnerButton.addActionListener((ActionEvent actionEvent) -> {
                client.setMapSize(8, 8, 10);
                client.notify();
                this.dispose();
            });
            intermediateButton.addActionListener((ActionEvent actionEvent) -> {
                client.setMapSize(16, 16, 40);
                client.notify();
                this.dispose();
            });
            experButton.addActionListener((ActionEvent actionEvent) -> {
                client.setMapSize(16, 30, 99);
                client.notify();
                this.dispose();
            });
            customButton.addActionListener((ActionEvent actionEvent) -> {
                SizeDialog sizeDialog = new SizeDialog(this);
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
                if(x == -1)
                    return;
                client.setMapSize(x, y, mines);
                client.notify();
                this.dispose();
            });
        }
        private void setSize(int x, int y, int mines){
            this.x = x;
            this.y = y;
            this.mines = mines;
        }
        private class SizeDialog extends JFrame{
            public SizeDialog(DifficultyDialog difficultyDialog){
                //Adds 3 inputs for width, height, and number of mines and a OK button
                JButton okButton = new JButton("OK");
                JPanel xInput = new JPanel();
                JPanel yInput = new JPanel();
                JPanel minesInput = new JPanel();
                JTextField xField = new JTextField();
                JTextField yField = new JTextField();
                JTextField minesField = new JTextField();
                JLabel xLabel = new JLabel("Width:");
                JLabel yLabel = new JLabel("Height:");
                JLabel minesLabel = new JLabel("Mines:");
                xInput.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
                yInput.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
                minesInput.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
                xInput.add(xLabel);
                xInput.add(xField);
                xInput.add(yLabel);
                xInput.add(yField);
                xInput.add(minesLabel);
                xInput.add(minesField);
                this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                this.add(xInput);
                this.add(yInput);
                this.add(minesInput);
                this.add(okButton);
                okButton.addActionListener((ActionEvent actionEvent) -> {
                    try {
                        int xVal = Integer.valueOf(xField.getText());
                        int yVal = Integer.valueOf(yField.getText());
                        int minesVal = Integer.valueOf(minesField.getText());
                        if(xVal < -1 || yVal < -1 || minesVal < -1) {
                            JDialog error = new JDialog(this, "Error");
                            error.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                            error.add(new JLabel("Error: Invalid Size"));
                            JButton okButtonD = new JButton("OK");
                            error.add(okButtonD);
                            okButtonD.addActionListener((ActionEvent actionEvent2) -> {
                                this.dispose();
                            });
                            error.setVisible(true);
                            return;
                        }
                        difficultyDialog.setSize(xVal, yVal, minesVal);
                    } catch (NumberFormatException e){
                        difficultyDialog.setSize(-1, -1, -1);
                    }
                    difficultyDialog.notify();
                    this.dispose();
                });
                difficultyDialog.setSize(400,100);
            }
        }
    }
	public void run(){ //Used for timer in the GUI

	}
}