import java.awt.BorderLayout;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;

import javax.swing.*;
import java.awt.*;

public class ServerUI extends JFrame{
	public Info info;
	ServerSocket serverSocket;
	int numPlayers;
	TextArea ta = new TextArea();
	ServerSideConnection ssc;
	Socket s;
	int playerID;
	ServerSideConnection player1 , player2, player3, player4;
	ServerUI(){
		setGUI();
	}
	private void setGUI(){
		setLayout(new BorderLayout());
		setSize(300,300);
		add(ta);
		ta.append("-----Game Start------\n");
		numPlayers = 0 ;
		try {
			serverSocket = new ServerSocket(4569);
		}catch(Exception e) {
			System.out.println("Exception from ServerUI");
		}
		setVisible(true);
		info = new Info();
	}
	
	public void acceptConnection(ServerUI serverUI) {
		try {
			serverUI.ta.append("Waiting for connecttions ....\n");
			while(numPlayers <4) {
				s = serverSocket.accept();
				numPlayers++;
				serverUI.ta.append("Player "+ numPlayers + "has connected\n");
				ssc = new ServerSideConnection(s,numPlayers);
				if(numPlayers == 1) {
					player1 = ssc;
				}else if(numPlayers == 2) {
					player2 = ssc;
				}else if(numPlayers ==3){
					player3 =ssc;
				} else if (numPlayers==4){
					player4 =ssc;
				}
				Thread t = new Thread(ssc);
				t.start();
				ssc.dataOutput.writeInt(numPlayers);
				ssc.dataOutput.flush();
			}
			System.out.println("now we have "+numPlayers +"player");
			
		}catch(IOException io) {}
	}
	
	public void acceptSelect(ServerUI serverUI) {
		try {
			int player1 = this.player1.dataInput.readInt();
			info.player1 = new Player(player1, new Character("character1"));
			serverUI.ta.append(info.player1+"\n");
			serverUI.ta.append("player " + player1 + " select....\n");
			int player2 = this.player2.dataInput.readInt();
			info.player2 = new Player(player2, new Character("character2"));
			serverUI.ta.append(info.player2+"\n");
			serverUI.ta.append("player " + player2 + " select....\n");
			int player3 = this.player3.dataInput.readInt();
			info.player3 = new Player(player3, new Character("character3"));
			serverUI.ta.append(info.player3+"\n");
			serverUI.ta.append("player " + player3 + " select....\n");
			int player4 = this.player4.dataInput.readInt();
			info.player4 = new Player(player4, new Character("character4"));
			serverUI.ta.append(info.player4+"\n");
			serverUI.ta.append("player " + player4 + " select....\n");

			this.player1.dataOutput.writeInt(4);
			this.player2.dataOutput.writeInt(4);
			this.player3.dataOutput.writeInt(4);
			this.player4.dataOutput.writeInt(4);

			ssc.dataOutput.flush();
		}catch(IOException io) {}
	}
	public static void main(String[] args) {
		ServerUI si = new ServerUI();
		si.acceptConnection(si);
		si.acceptSelect(si);
		si.acceptSelect(si);
	}
}

class ServerSideConnection implements Runnable{
	Socket socket;
	DataInputStream dataInput ;
	DataOutputStream dataOutput;
	int playerID;
	ServerSideConnection(Socket s , int id){
		socket = s;
		playerID = id;
		try {
			dataInput = new DataInputStream(socket.getInputStream());
			dataOutput= new DataOutputStream(socket.getOutputStream());
		}catch(IOException ex) {System.out.println("IOException from SSC");}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			dataOutput.writeInt(playerID);
			dataOutput.flush();
		}catch(IOException ex) {System.out.println("IOException from run() SSC");} 
	}
}
