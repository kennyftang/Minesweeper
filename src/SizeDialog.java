import javax.swing.*;
import java.awt.event.ActionEvent;

public class SizeDialog extends JFrame {
    public SizeDialog(DifficultyDialog difficultyDialog, GameState gameState) {
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
                if (xVal < -1 || yVal < -1 || minesVal < -1) {
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
                gameState.setMap(xVal, yVal, minesVal);
            } catch (NumberFormatException e){}
            difficultyDialog.notify();
            this.dispose();
        });
        difficultyDialog.setSize(400, 100);
    }
}