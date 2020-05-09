import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class ClientCM {

	private InetAddress ip;
	private int port;
	private DatagramSocket socket;
	byte buf[] = null; 

	ClientCM(String ipAddr, int cmPort) throws UnknownHostException, SocketException
	{
		this.port = cmPort;
		this.socket = new DatagramSocket();
		this.ip = InetAddress.getByName(ipAddr);
	}


	public String send(String msg) throws IOException 
	{
		buf = Compressor.compress(msg);
//				buf = msg.getBytes();
		DatagramPacket packet = new DatagramPacket(buf, buf.length, this.ip, this.port);
		socket.send(packet);
		this.buf = new byte[65507]; 
		packet = new DatagramPacket(buf, buf.length);
		socket.receive(packet);
		System.out.println("Recived");
		//		Decompress received data 
		byte [] compressedData = packet.getData();
		String decompressedData = "";
		decompressedData = Compressor.decompress(compressedData);

		System.out.println(decompressedData);

		String received = new String(decompressedData);
		System.out.println(received);
		//		String received = new String(packet.getData(), 0, packet.getLength());
		return received;
	}


	public void closeConncection() 
	{
		this.socket.close();
	}
}
