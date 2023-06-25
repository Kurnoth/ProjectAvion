package ProjectAvion.src;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InitFrame extends JDialog {
    private JPanel initPanel;
    private JTextField latitudeField;
    private JTextField longitudeField;
    private JButton okButton;
    private double latitudeInput;
    private double longitudeInput;

    public InitFrame() {
        setContentPane(initPanel);
        setTitle("Initialisation");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //open in the center of the screen
        setLocationRelativeTo(null);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    latitudeInput = Double.parseDouble(latitudeField.getText());
                    longitudeInput = Double.parseDouble(longitudeField.getText());
                    Client.createRadar(latitudeInput, longitudeInput);

                    //close window
                    setVisible(false);
                    dispose();
                }
                catch (NumberFormatException event) {
                    JOptionPane.showMessageDialog(null, "La latitude et/ou la longitude ne sont pas des nombres", "Erreur", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        InitFrame init = new InitFrame();
    }
}
