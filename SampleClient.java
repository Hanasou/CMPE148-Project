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
	private File clientDirectory = null;
	
	public SampleClient(String host, int port) {
		try {
			System.out.println("Connecting");
			client = new Socket(host, port);
			System.out.println("Connected");
			stdin = new BufferedReader(new InputStreamReader(System.in));
			in = new DataInputStream(System.in);
			out = new DataOutputStream(client.getOutputStream());
			clientDirectory = new File("client_directory");
		}
		catch (IOException e) {
			System.out.println(e);
		}
		System.out.println("Welcome. Type /help for commands, /q to quit.");
		String input = "";
		while (!input.equals("/q")) {
			try {
				input = stdin.readLine();
				
				if (input.equals("/help")) {
					System.out.println("'/check server dir' to look inside server directory");
					System.out.println("'/check client dir' to look inside client directory");
					System.out.println("'/download' to download a file from the server directory");
					System.out.println("'/upload' to upload a file to the server directory");
					System.out.println("'/create' to make a new file into client directory");
					System.out.println("'/write' to write data on an existing file");
				}
				else if (input.equals("/create")) {
					System.out.println("Enter file name: ");
					String fileName = stdin.readLine();
					File file = new File("client_directory/" + fileName);
					//file.createNewFile();
					if (file.createNewFile()) {
						System.out.println("Created file: " + fileName);
					}
					else {
						System.out.println("File already exists");
					}
				}
				else if (input.equals("/check client dir")) {
					String [] files = clientDirectory.list();
					for (int i = 0; i < files.length - 1; i++) {
						System.out.println(files[i]);
					}
				}
				else if (input.equals("/write")) {
					System.out.println("Enter file name");
					String fileName = stdin.readLine();
					File file = new File("client_directory/" + fileName);
					if (file.exists()) {
						System.out.println("Write data: ");
						FileWriter writer = new FileWriter(file);
						String writeData = stdin.readLine();
						writer.write(writeData);
						System.out.println("File modified");
						writer.close();
					}
					else {
						System.out.println("File not found");
					}
				}
				else {
					
				}
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
