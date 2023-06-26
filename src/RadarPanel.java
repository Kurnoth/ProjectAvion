package ProjectAvion.src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class RadarPanel extends JPanel {

    //radius of the point representing a plane
    int smallCircleRadius = 10;

    //structure to store the data of the point
    private class PlanePoint {
        int pointX;
        int pointY;
        int flightNumber;

        public PlanePoint(int pointX, int pointY, int flightNumber) {
            this.pointX = pointX;
            this.pointY = pointY;
            this.flightNumber = flightNumber;
        }
    }

    //list of point
    private ArrayList<PlanePoint> planePoints;

    public void updatePoint() {
        planePoints.clear();
        for (Avion plane : Client.getListAvions()) {
            double latitudeFromCenter = Client.getRadar().getLongitude() - plane.getLongitude();
            double longitudeFromCenter = Client.getRadar().getLatitude() - plane.getLatitude();

            double smallCircleX = (100 * longitudeFromCenter) / scale - smallCircleRadius;
            double smallCircleY = (100 * latitudeFromCenter) / scale - smallCircleRadius;
            planePoints.add(new PlanePoint((int) smallCircleX, (int) smallCircleY, plane.getFlightNumber()));
        }
    }
    
    public RadarPanel() {
        planePoints = new ArrayList<>();
        setVisible(true);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //handle mouse click event here
                int mouseX = e.getX();
                int mouseY = e.getY();
                mouseX -= getWidth() / 2;
                mouseY -= getHeight() / 2;

                //check if click inside a point
                for (int i = 0 ; i < planePoints.size() ; i++) {
                    if (mouseX >= planePoints.get(i).pointX && mouseX <= planePoints.get(i).pointX + smallCircleRadius * 2 && mouseY >= planePoints.get(i).pointY && mouseY <= planePoints.get(i).pointY + smallCircleRadius * 2) {
                        Client.getMainFrame().setFlightNumberField(Client.getListAvions().get(i).getFlightNumber());
                        Client.getMainFrame().setSpeedField(Client.getListAvions().get(i).getVitesse());
                        Client.getMainFrame().setAltitudeField(Client.getListAvions().get(i).getAltitude());
                        Client.getMainFrame().setCapField(Client.getListAvions().get(i).getCap());
                    }
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //set the color and draw the circle
        g.setColor(Color.BLACK);
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = Math.min(centerX, centerY) - 10;
        g.drawOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
        g.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
        g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);

        //set the color and draw the plane point
        g.setColor(Color.BLACK);
        for (int i = 0 ; i < planePoints.size() ; i++) {
            g.drawOval(centerX + planePoints.get(i).pointX, centerY + planePoints.get(i).pointY, smallCircleRadius * 2, smallCircleRadius * 2);

            //draw the cross inside the small circle
            g.drawLine(centerX + planePoints.get(i).pointX + smallCircleRadius, centerY + planePoints.get(i).pointY, centerX + planePoints.get(i).pointX + smallCircleRadius, centerY +  planePoints.get(i).pointY + smallCircleRadius * 2);
            g.drawLine(centerX + planePoints.get(i).pointX, centerY + planePoints.get(i).pointY + smallCircleRadius, centerX + planePoints.get(i).pointX + smallCircleRadius * 2, centerY + planePoints.get(i).pointY + smallCircleRadius);

            //draw text above the small circle
            String text1 = "C: " + Client.getListAvions().get(i).getCap();
            int text1X = centerX + planePoints.get(i).pointX;
            int text1Y = centerY + planePoints.get(i).pointY - smallCircleRadius - 3;
            g.drawString(text1, text1X, text1Y);

            String text2 = "A: " + Client.getListAvions().get(i).getAltitude();
            int text2X = centerX + planePoints.get(i).pointX;
            int text2Y = centerY + planePoints.get(i).pointY - smallCircleRadius + 7;
            g.drawString(text2, text2X, text2Y);
        }
    }
}
