import javax.swing.*;
import java.awt.event.ActionEvent;

public class DifficultyDialog extends JFrame {
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
        GameState gameState = client.getGameState();
        beginnerButton.addActionListener((ActionEvent actionEvent) -> {
            gameState.setMap(8, 8, 10);
            client.notify();
            this.dispose();
        });
        intermediateButton.addActionListener((ActionEvent actionEvent) -> {
            gameState.setMap(16, 16, 40);
            client.notify();
            this.dispose();
        });
        experButton.addActionListener((ActionEvent actionEvent) -> {
            gameState.setMap(16, 30, 99);
            client.notify();
            this.dispose();
        });
        customButton.addActionListener((ActionEvent actionEvent) -> {
            SizeDialog sizeDialog = new SizeDialog(this, client.getGameState());
            sizeDialog.setSize(300, 300);
            sizeDialog.setVisible(true);
            sizeDialog.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
            client.notify();
            this.dispose();
        });
    }
}