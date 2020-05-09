import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import java.util.Scanner;

public class ServerCM extends Thread {

	private DatagramSocket socket;
	byte[] received = new byte[65507];
	Dispatcher dispatcher;


	ServerCM() throws SocketException {
		// Create a socket to listen at port 8080 by default 
		this.socket = new DatagramSocket(8080);
		this.dispatcher = new Dispatcher();
		this.dispatcher.registerObject(new User(), "User"); 
		this.dispatcher.registerObject(new Song(), "Song"); 
		this.dispatcher.registerObject(new Playlist(), "Playlist");
		this.dispatcher.registerObject(new SongDispatcher(), "SongDispatcher");
	}

	ServerCM(int port) throws SocketException {
		// Create a socket to listen at given port 
		this.socket = new DatagramSocket(port);
		this.dispatcher = new Dispatcher();
		this.dispatcher.registerObject(new User(), "User"); 
		this.dispatcher.registerObject(new Song(), "Song"); 
		this.dispatcher.registerObject(new Playlist(), "Playlist");
		this.dispatcher.registerObject(new SongDispatcher(), "SongDispatcher");
	}


	public void run() {
		DatagramPacket packet = new DatagramPacket(this.received, this.received.length);
		try { socket.receive(packet); } 
		catch (IOException e) { e.printStackTrace(); }


		InetAddress address = packet.getAddress();
		int port = packet.getPort();

		//		Decompress received data 
		byte [] compressedData = packet.getData();
		String decompressedData="";

		try { decompressedData = Compressor.decompress(compressedData);	} 
		catch (IOException e1) { e1.printStackTrace(); }


		//		String received = new String(packet.getData(), 0, packet.getLength());
		String received = decompressedData;
		System.out.println("Server got after decompress: " + received);
		String ret = this.dispatcher.dispatch(received);

		System.out.println("Ret: " + ret);
		//		byte[] bret = ret.getBytes();


		byte[] bret = null;
		try {bret = Compressor.compress(ret);}
		catch (IOException e1) { e1.printStackTrace(); }



		System.out.println("Server bret size: " + bret.length);
		packet = new DatagramPacket(bret, bret.length, address, port);
		System.out.println("Bret Length: " + bret.length);

		try { socket.send(packet); } 
		catch (IOException e) { e.printStackTrace(); }
		
	}

	private void close() {
		this.socket.close();
	}


	public static void main(String[] args) throws SocketException {
		ServerCM server = new ServerCM(8082);
		for (int i = 0; i < 100; i++) {
			server.run();	
		}
		Scanner myObj = new Scanner(System.in);
		myObj.nextLine();
		server.close();
	}
}

