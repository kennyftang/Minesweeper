import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

class SizeDialog extends JFrame {
    SizeDialog(DifficultyDialog difficultyDialog, GameState gameState) {
        //Adds 3 inputs for width, height, and number of mines and a OK button
        JButton okButton = new JButton("OK");
        JPanel xInput = new JPanel();
        JPanel yInput = new JPanel();
        JPanel minesInput = new JPanel();
        JPanel inputs = new JPanel();
        JTextField xField = new JTextField();
        JTextField yField = new JTextField();
        JTextField minesField = new JTextField();
        JLabel xLabel = new JLabel("Width:");
        JLabel yLabel = new JLabel("Height:");
        JLabel minesLabel = new JLabel("Mines:");
        xInput.setLayout(new BoxLayout(xInput, BoxLayout.X_AXIS));
        yInput.setLayout(new BoxLayout(yInput, BoxLayout.X_AXIS));
        minesInput.setLayout(new BoxLayout(minesInput, BoxLayout.X_AXIS));
        inputs.setLayout(new BoxLayout(inputs, BoxLayout.Y_AXIS));
        xField.setMaximumSize(new Dimension(50, 20));
        yField.setMaximumSize(new Dimension(50, 20));
        minesField.setMaximumSize(new Dimension(50, 20));
        xInput.add(xLabel);
        xInput.add(xField);
        yInput.add(yLabel);
        yInput.add(yField);
        minesInput.add(minesLabel);
        minesInput.add(minesField);
//        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        this.setLayout(new BorderLayout());
        inputs.add(xInput);
        inputs.add(yInput);
        inputs.add(minesInput);
        this.add(inputs, BorderLayout.CENTER);
        this.add(okButton, BorderLayout.SOUTH);
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
                    okButtonD.addActionListener((ActionEvent actionEvent2) -> this.dispose());
                    error.setVisible(true);
                    return;
                }
                gameState.setMap(xVal, yVal, minesVal);
                difficultyDialog.dispose();
                this.dispose();
            } catch (NumberFormatException e){
                this.dispose();
            }
        });
    }
}