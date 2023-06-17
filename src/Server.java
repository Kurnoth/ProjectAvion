package ProjectAvion.src;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

public class Server {
    
	private static final int SERVER_PORT = 2000;
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        
        DatagramSocket ds = new DatagramSocket(SERVER_PORT);
        byte[] receive = new byte[65535];
		ArrayList<Avion> avionList = Data.initAvions();


        while (true) {

		
            DatagramPacket dpReceive = new DatagramPacket(receive, receive.length);
            ds.receive(dpReceive);	
			
			byte[] data = dpReceive.getData();
			int dataSize = dpReceive.getLength();

			System.out.println(dataSize);
			if (dataSize != 0) {
				ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
				ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
				Avion avion = (Avion) objectInputStream.readObject();
				System.out.println(avion);
				//appelle de fonction pour changer l'avion dans la liste
			} else {

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
}
