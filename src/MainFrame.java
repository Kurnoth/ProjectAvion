package ProjectAvion.src;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;


public class MainFrame extends JFrame {

    private JPanel mainPanel;
    private JTextField flightNumberField;
    private JTextField speedField;
    private JTextField altitudeField;
    private JTextField capField;
    private JPanel radarDisplay;
    private JButton sendButton;
    private RadarPanel radarPanel;

    public MainFrame() {
        setContentPane(mainPanel);
        setTitle("Client");
        setSize(1000, 700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        //center the window in the middle of the screen
        setLocationRelativeTo(null);
        //block manuel resize
        setResizable(false);

        //create an instance of RadarPanel and set it as the panel for radarDisplay
        radarPanel = new RadarPanel();
        radarDisplay.setLayout(new BorderLayout());
        radarDisplay.add(radarPanel);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Avion modifiedAvion = null;
                for (Avion plane : Client.getListAvions()) {
                    if (plane.getFlightNumber() == Integer.parseInt(flightNumberField.getText())) {
                        plane.setVitesse(Integer.parseInt(speedField.getText()));
                        plane.setAltitude((Integer.parseInt(altitudeField.getText())));
                        plane.setCap(Integer.parseInt(capField.getText()));
                        modifiedAvion = plane;
                    }
                }

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream;
                try {
                    objectOutputStream = new ObjectOutputStream(outputStream);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    objectOutputStream.writeObject(modifiedAvion);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                byte[] sendData = outputStream.toByteArray();
                DatagramPacket avionToSend = new DatagramPacket(sendData, sendData.length, Client.getIp(), Client.getServerPort());
                try {
                    Client.getDs().send(avionToSend);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void setFlightNumberField(int flightNumber) {
        flightNumberField.setText(Integer.toString(flightNumber));
    }

    public void setSpeedField(int speed) {
        speedField.setText(Integer.toString(speed));
    }

    public void setAltitudeField(int altitude) {
        altitudeField.setText(Integer.toString(altitude));
    }

    public void setCapField(int cap) {
        capField.setText(Integer.toString(cap));
    }

    public RadarPanel getRadarPanel() {
        return radarPanel;
    }
}
