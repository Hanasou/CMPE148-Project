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
	private DataInputStream in = null;
	private DataOutputStream out = null;
	private File serverDirectory = null;
	//private DataOutputStream out = null;

	public SampleServer(int port) {
		try {
			//Set up a server with this port.
			server = new ServerSocket(port);
			System.out.println("Server Active");
			
			client = server.accept();
			System.out.println("Client Connected");
			System.out.println("Server Log");
			in = new DataInputStream(new BufferedInputStream(client.getInputStream()));
			out = new DataOutputStream(client.getOutputStream());
			serverDirectory = new File("server_directory");
			
			String input = "";
			while (!input.equals("/q")) {
				try {
					input = in.readUTF();
					System.out.println(input);
					if (input.equals("/check server dir")) {
						String[] files = serverDirectory.list();
						StringBuilder builder = new StringBuilder();
						for (String s : files) {
							builder.append(s + '\n');
						}
						out.writeUTF(builder.toString());
					}
				}
				catch(IOException e) {
					System.out.println(e);
				}
			}
			System.out.println("Connected Ended");
			client.close();
			out.close();
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
