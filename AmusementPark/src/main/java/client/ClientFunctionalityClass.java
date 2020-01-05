package client;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientFunctionalityClass {

	private PrintWriter out;
	private BufferedReader br;
	private Socket socket;
	
	public ClientFunctionalityClass() throws IOException {
		sendRequestToNode();
	}

	private void sendRequestToNode() throws IOException {
		br = new BufferedReader(new FileReader("ClientFile/ClientFile.txt"));
		String line = br.readLine();
		while(line != null) {
			String[] tokenizedMessage = line.split(" ");
			
		}
	}
}
