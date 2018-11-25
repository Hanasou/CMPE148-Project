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
			
			//This sets up the connection between client and server.
			client = server.accept();
			System.out.println("Client Connected");
			System.out.println("Server Log");
			//Input and output streams for receiving/sending information between client/server.
			//Takes information from client (input stream) and sends information to client (output stream).
			in = new DataInputStream(new BufferedInputStream(client.getInputStream()));
			out = new DataOutputStream(client.getOutputStream());
			//The directory where all the files on the server are located.
			serverDirectory = new File("server_directory");
			
			String input = "";
			while (!input.equals("/q")) {
				try {
					input = in.readUTF();
					System.out.println(input);
					//If client inputs /check server dir then the server does this.
					if (input.equals("/check server dir")) {
						String[] files = serverDirectory.list();
						StringBuilder builder = new StringBuilder();
						for (String s : files) {
							builder.append(s + '\n');
						}
						//server writes out the list of files into the client's output stream. Client will read from it and display on its console.
						out.writeUTF(builder.toString());
					}
					else if (input.equals("/download")) {
						String fileName = in.readUTF();
						System.out.println("Sending: " + fileName);
						File file = new File("server_directory/" + fileName);
						byte[] bytes = new byte[1024];
						FileInputStream fileIn = new FileInputStream(file);
						int count;
						while ((count = fileIn.read(bytes)) > 0) {
				            out.write(bytes, 0, count);
				        }
						System.out.println("Sent");
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
