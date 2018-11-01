package connectiontest;

import java.net.*;
import java.io.*;
import java.util.*;

/**
 * Sample Client class to connect to the server. Play around with this to see how this works.
 */
public class SampleClient {

	//start this program to connect to server
	public static void main(String[] args) {
		try {
			System.out.println("Connecting");
			//connects to localhost on this port.
			Socket client = new Socket("localhost", 6066);
		}
		catch (IOException e) {
			System.out.println(e);
		}
	}
}
