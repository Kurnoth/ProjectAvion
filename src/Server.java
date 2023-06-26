package ProjectAvion.src;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Server {
    
	private static final int SERVER_PORT = 2000;
	private static final String URL = "jdbc:mysql://localhost:3306/AvionsData";
	//don't forget to change your id if necessary
	private static final String USER = "root";
	private static final String PASS = "password";

	static Connection connexion = null;
	static PreparedStatement statement = null;

    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
        DatagramSocket ds = new DatagramSocket(SERVER_PORT);
        byte[] receive = new byte[65535];
		ArrayList<Avion> avionList = Data.initAvions();

		//establish connexion with the database
		connexion = DriverManager.getConnection(URL, USER, PASS);
		//prepare the insert query
		String requete = "INSERT INTO AvionsData (FlightNumber, Latitude, Longitude, Vitesse, Altitude, Cap) VALUES (?, ?, ?, ?, ?, ?)";
		statement = connexion.prepareStatement(requete);

		//execute the query
		for (Avion a : avionList) {
			statement.setInt(1, a.getFlightNumber());
			statement.setDouble(2, a.getLatitude());
			statement.setDouble(3, a.getLongitude());
			statement.setInt(4, a.getVitesse());
			statement.setInt(5, a.getAltitude());
			statement.setInt(6, a.getCap());
			statement.executeUpdate();
		}
		//close after finishing
		statement.close();
		connexion.close();

		Thread updateThread = new Thread(new DataUpdater());
		updateThread.start();


        while (true) {
            DatagramPacket dpReceive = new DatagramPacket(receive, receive.length);
            ds.receive(dpReceive);	
			
			byte[] data = dpReceive.getData();
			int dataSize = dpReceive.getLength();

			if (dataSize != 0) {
				ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
				ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
				Avion avion = (Avion) objectInputStream.readObject();

				connexion = DriverManager.getConnection(URL, USER, PASS);
				requete = "INSERT INTO Ordre (FlightNumber, Vitesse, Altitude, Cap) VALUES (?, ?, ?, ?)";
				statement = connexion.prepareStatement(requete);
				statement.setInt(1, avion.getFlightNumber());
				statement.setInt(2, avion.getVitesse());
				statement.setInt(3, avion.getAltitude());
				statement.setInt(4, avion.getCap());
				statement.executeUpdate();
				statement.close();
				connexion.close();

				for (Avion plane : avionList) {
					if (plane.getFlightNumber() == avion.getFlightNumber()) {
						plane.setVitesse(avion.getVitesse());
						plane.setAltitude(avion.getAltitude());
						plane.setCap(avion.getCap());
					}
				}
			}
			else {
				InetAddress clientAddress = dpReceive.getAddress();
				int clientPort = dpReceive.getPort();

				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
				objectOutputStream.writeObject(avionList);
				objectOutputStream.flush();
				byte[] sendData = outputStream.toByteArray();
				DatagramPacket packetToSend = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
				ds.send(packetToSend);
				}

			//faire le break
        }

    }

	private static class DataUpdater implements Runnable {
		@Override
		public void run() {
			try {
				while (true) {
					Data.updatePosition();
					connexion = DriverManager.getConnection(URL, USER, PASS);
					String requete = "INSERT INTO AvionsData (FlightNumber, Latitude, Longitude, Vitesse, Altitude, Cap) VALUES (?, ?, ?, ?, ?, ?)";
					statement = connexion.prepareStatement(requete);

					for (Avion a : Data.getListeAvions()) {
						statement.setInt(1, a.getFlightNumber());
						statement.setDouble(2, a.getLatitude());
						statement.setDouble(3, a.getLongitude());
						statement.setInt(4, a.getVitesse());
						statement.setInt(5, a.getAltitude());
						statement.setInt(6, a.getCap());
						statement.executeUpdate();
					}
					statement.close();
					connexion.close();

					Thread.sleep(15000);
				}
			} catch (InterruptedException | SQLException e) {
					e.printStackTrace();
			}
		}
	}
}
