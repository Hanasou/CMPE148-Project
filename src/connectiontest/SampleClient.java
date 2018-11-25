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
			//sets up connection between designated host and port
			client = new Socket(host, port);
			System.out.println("Connected");
			//initializes inputstreams and output streams used for client/server communication.
			stdin = new BufferedReader(new InputStreamReader(System.in));
			//This is the client's input stream. This is how the client receives information from the server.
			in = new DataInputStream(client.getInputStream());
			//This is the client's output stream. We write onto the server with out.writeUTF. This is how the server receives information.
			out = new DataOutputStream(client.getOutputStream());
			//this kind of represents the client's files. This current program only uploads from and downloads to a single directory for the sake of
			//simplicity. If you guys want to do have a way the user can select the directory to upload/download to/on, then that would be nice
			//but it's not really that important I guess.
			clientDirectory = new File("client_directory");
		}
		catch (IOException e) {
			System.out.println(e);
		}
		System.out.println("Welcome. Type /help for commands, /q to quit.");
		String input = "";
		while (!input.equals("/q")) {
			try {
				//Buffered reader takes in System.in.
				input = stdin.readLine();
				//The DataOutputStream is writing in UTF (Unicode Transformation Format. Read up on it, we might have to talk about it in our presentation)
				//Server will read from its input stream and will know what to do.
				out.writeUTF(input);
				if (input.equals("/help")) {
					System.out.println("'/check server dir' to look inside server directory");
					System.out.println("'/check client dir' to look inside client directory");
					System.out.println("'/download' to download a file from the server directory");
					System.out.println("'/upload' to upload a file to the server directory");
					System.out.println("'/create' to make a new file into client directory");
					System.out.println("'/write' to write data on an existing file");
				}
				//Creates a file in the client directory.
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
				//Lists all files in client_directory
				else if (input.equals("/check client dir")) {
					String [] files = clientDirectory.list();
					for (int i = 0; i < files.length; i++) {
						System.out.println(files[i]);
					}
				}
				//Communicates with server to see the contents of its directory. Since this is technically on my machine I don't have to do this, but
				//pretend it's not.
				else if (input.equals("/check server dir")) {
					System.out.println("Checking Files in Server Directory");
					System.out.println(in.readUTF());
				}
				//Writes on an existing file.
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
				else if (input.equals("/download")) {
					//TODO: Looks at a file from the server_directory and writes it onto the client_directory
					System.out.println("Enter name of the file you want to download");
					String fileName = stdin.readLine();
					out.writeUTF(fileName);
					File file = new File("client_directory/" + fileName);
					file.createNewFile();
					FileOutputStream fileOut = new FileOutputStream("client_directory/" + fileName);
					byte[] bytes = new byte[1024];
					int count = in.read(bytes);
					System.out.println("File received. Downloading: ");
					fileOut.write(bytes, 0, count);
				    System.out.println("Downloaded");
				}
				else if (input.equals("/upload")) {
					//TODO: Looks at a file from the client_directory and writes it onto the server_directory
				}
				else {
					
				}
			}
			catch (IOException e) {
				System.out.println(e);
			}
		}
		System.out.println("Connected Ended");
		
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
