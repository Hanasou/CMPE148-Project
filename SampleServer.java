package connectiontest;

import java.net.*;
import java.io.*;
import java.util.*;

/**
 * Sample server class to connect to. Start this first to set up the server. Play around with this to see how this all works.
 */
public class SampleServer {

	private static Socket socket;
	private static ServerSocket server;

	public static void main(String[] args) throws IOException {
		try {
			//Set up a server with this port.
			server = new ServerSocket(6066);
			System.out.println("Server Active");
			
			//socket = server.accept();
			//System.out.println("Client Connected");
		}
		catch (IOException e) {
			System.out.println(e);
		}
		
		//infinite loop to keep connection going
		while (true) {
			//ServerSocket accepts client connections
			socket = server.accept();
			System.out.println("Client Connected");
		}
	}
}
