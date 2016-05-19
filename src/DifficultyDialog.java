import javax.swing.*;
import java.awt.event.ActionEvent;

class DifficultyDialog extends JFrame {
    DifficultyDialog(Minesweeper client) {
        JButton beginnerButton = new JButton("Beginner");
        JButton intermediateButton = new JButton("Intermediate");
        JButton expertButton = new JButton("Expert");
        JButton customButton = new JButton("Custom");
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        this.add(beginnerButton);
        this.add(intermediateButton);
        this.add(expertButton);
        this.add(customButton);
        GameState gameState = client.getGameState();
        beginnerButton.addActionListener((ActionEvent actionEvent) -> {
            gameState.setMap(8, 8, 10);
            this.dispose();
        });
        intermediateButton.addActionListener((ActionEvent actionEvent) -> {
            gameState.setMap(16, 16, 40);
            this.dispose();
        });
        expertButton.addActionListener((ActionEvent actionEvent) -> {
            gameState.setMap(16, 30, 99);
            this.dispose();
        });
        customButton.addActionListener((ActionEvent actionEvent) -> {
            SizeDialog sizeDialog = new SizeDialog(this, client.getGameState());
            sizeDialog.setSize(300, 300);
            sizeDialog.pack();
            sizeDialog.setVisible(true);
            sizeDialog.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        });
    }
}