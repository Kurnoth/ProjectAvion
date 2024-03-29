package ProjectAvion.src;

import java.io.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {

    private static final int SERVER_PORT = 2000;
    private static InetAddress ip;
    private static DatagramSocket ds;
    private static ArrayList<Avion> listAvions = new ArrayList<>();
    private static Pos radar;
    private static MainFrame mainFrame;
    static String input;

    public static void main(String[] args) throws IOException {
        InitFrame initFrame = new InitFrame();
        //instruction to wait for window close before continuing
        initFrame.setModal(true);
        initFrame.setVisible(true);

        mainFrame = new MainFrame();

        Thread getDataThread = new Thread(new DataRequester());
        getDataThread.start();


        ip = InetAddress.getLocalHost();
        ds = new DatagramSocket();

        input = "";
        StringReader stringReader = new StringReader(input);
        Scanner sc = new Scanner(stringReader);

        while (true) {
            String inp = sc.nextLine();

            String[] tab = inp.split(" ");
            if (tab.length < 4) {
                System.out.println("Pas assez de données");
            } else {
                int[] intTab = new int[tab.length];
                for (int i = 0; i < tab.length; i++) {
                    intTab[i] = Integer.parseInt(tab[i]);
                }

                if (intTab[3] > 360 || intTab[1] < 0 || intTab[2] < 0 || intTab[3] < 0) {
                    System.out.println("Erreurs dans les données envoyées");

                } else if (checkFlight(intTab[0]) == false) {
                    System.out.println("Mauvais numéro de vol");
                } else {
                    Avion modifiedAvion = modifyAvion(intTab);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                    objectOutputStream.writeObject(modifiedAvion);
                    byte[] sendData = outputStream.toByteArray();
                    DatagramPacket avionToSend = new DatagramPacket(sendData, sendData.length, ip, SERVER_PORT);
                    ds.send(avionToSend);
                }
            }
        }
    }

    public static boolean checkFlight(int numero) {
        for (Avion a : listAvions) {
            if (a.getFlightNumber() == numero)
                return true;
        }
        return false;
    }

    public static Avion modifyAvion(int[] tab) {
        for ( Avion a : listAvions) {
            if (a.getFlightNumber() == tab[0]) {
                a.setVitesse(tab[1]);
                a.setAltitude(tab[2]);
                a.setCap(tab[3]);
                return a;
            }
        }
        return null;
    }

    private static class DataRequester implements Runnable {
        
        @Override
        public void run() {
            try {
                Map r = new Map(radar);
                while (true) {
                    Client.listAvions = getData();
                    r.display(listAvions);
                    mainFrame.getRadarPanel().updatePoint();
                    mainFrame.getRadarPanel().repaint();
                    Thread.sleep(16000);
                }
            } catch (InterruptedException | IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        private ArrayList<Avion> getData() throws IOException, ClassNotFoundException {

            InetAddress ip = InetAddress.getLocalHost();
            DatagramSocket ds = new DatagramSocket();
            byte[] emptyData = new byte[0];
            DatagramPacket emptyPacket = new DatagramPacket(emptyData, emptyData.length, ip, SERVER_PORT);
            ds.send(emptyPacket);

            byte[] receiveData = new byte[65535];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            ds.receive(receivePacket);
            byte[] data = receivePacket.getData();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            ArrayList<Avion> avionFromServer = (ArrayList<Avion>) objectInputStream.readObject();

            return avionFromServer;
        }
    }

    public static void createRadar(double latitude, double longitude) {
        radar = new Pos(latitude, longitude);
    }

    public static ArrayList<Avion> getListAvions() {
        return listAvions;
    }

    public static Pos getRadar() {
        return radar;
    }

    public static int getServerPort() {
        return SERVER_PORT;
    }

    public static InetAddress getIp() {
        return ip;
    }

    public static DatagramSocket getDs() {
        return ds;
    }

    public static MainFrame getMainFrame() {
        return mainFrame;
    }
}
