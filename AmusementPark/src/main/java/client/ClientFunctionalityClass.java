package client;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

public class ClientFunctionalityClass {

	private PrintWriter out;
	private BufferedReader br;
	private Socket socket;
	
	public ClientFunctionalityClass() throws IOException {
		sendRequestToNode();
	}

	private void sendRequestToNode() throws IOException {
		br = new BufferedReader(new FileReader("ClientFile/ClientFile.txt"));
		String line;
		while((line= br.readLine()) != null) {
			String[] tokenizedMessage = line.split(" ");
			socket = new Socket(tokenizedMessage[2], Integer.parseInt(tokenizedMessage[3]));
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println(tokenizedMessage[0] + " " + tokenizedMessage[1]);
			System.out.println(tokenizedMessage[0] + " " + tokenizedMessage[1]);
			out.close();
		}
		
		
		
	}
}
