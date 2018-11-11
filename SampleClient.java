package connectiontest;

import java.net.*;
import java.io.*;
import java.util.*;

/**
 * Sample Client that does nothing.
 */
public class SampleClient {

	private Socket client = null;
	private BufferedReader stdin = null;
	private DataInputStream in = null;
	private DataOutputStream out = null;
	
	public SampleClient(String host, int port) {
		try {
			System.out.println("Connecting");
			client = new Socket(host, port);
			System.out.println("Connected");
			stdin = new BufferedReader(new InputStreamReader(System.in));
			in = new DataInputStream(System.in);
			out = new DataOutputStream(client.getOutputStream());
		}
		catch (IOException e) {
			System.out.println(e);
		}
		
		String input = "";
		while (!input.equals("Over")) {
			try {
				input = stdin.readLine();
				out.writeUTF("Client Writes: " + input);
			}
			catch (IOException e) {
				System.out.println(e);
			}
		}
		System.out.println("Connected Ended");
		//System.out.println("What do you want to do here?");
		//System.out.println("(1) Upload File");
		//System.out.println("(2) Download File");
		
		try {
			in.close();
			stdin.close();
			out.close();
			client.close();
		}
		catch (IOException e) {
			System.out.println(e);
		}
	}
	//start this program to connect to server
	public static void main(String[] args) {
		SampleClient client = new SampleClient("localhost", 6000);
	}
}
