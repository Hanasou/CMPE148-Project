package connectiontest;

import java.net.*;
import java.io.*;
import java.util.*;

/**
 * Sample server class to connect to. Start this first to set up the server. Play around with this to see how this all works.
 */
public class SampleServer {

	private static Socket client = null;
	private static ServerSocket server = null;
	//private BufferedReader stdin = null;
	private DataInputStream in = null;
	//private DataOutputStream out = null;

	public SampleServer(int port) {
		try {
			//Set up a server with this port.
			server = new ServerSocket(port);
			System.out.println("Server Active");
			
			client = server.accept();
			System.out.println("Client Connected");
			
			//stdin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			in = new DataInputStream(new BufferedInputStream(client.getInputStream()));
			
			String input = "";
			while (!input.equals("Client Writes: Over")) {
				try {
					input = in.readUTF();
					System.out.println(input);
				}
				catch(IOException e) {
					System.out.println(e);
				}
			}
				client.close();
				//stdin.close();
				in.close();
		}
		catch (IOException e) {
			System.out.println(e);
		}
	}
	public static void main(String[] args) {
		SampleServer server = new SampleServer(6000);
		
	}
}
