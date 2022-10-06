import java.io.*;
import java.net.*;

public class Client {
	
}

class ClientSideConnection{
	Socket socket;
	DataInputStream dataIn;
	DataOutputStream dataOut;
	ByteArrayOutputStream bo ;
	ObjectOutputStream so ;
	int playerID; 
	int numPlayer;
	public ClientSideConnection() {
		System.out.println("----Client----");
		try {
			socket = new Socket("localhost",4569 );
			dataIn = new DataInputStream(socket.getInputStream());
			dataOut = new DataOutputStream(socket.getOutputStream());
			bo = new ByteArrayOutputStream();
			so = new ObjectOutputStream(bo);
			playerID = dataIn.readInt();
			numPlayer = dataIn.readInt();
			System.out.println("Connected to server as Player" + playerID);
			System.out.println("We now have "+numPlayer+"in server.");
		}catch(IOException ex) { System.out.println("IO Exception from csc constructor");}
		
	}
}
