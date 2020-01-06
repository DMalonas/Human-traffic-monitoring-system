package client;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

public class ClientFunctionalityClass {


	private Socket socket;
	private PrintWriter out;
	private BufferedReader br;
	
	public ClientFunctionalityClass() throws IOException {
		sendRequestToNode();
	}

	private void sendRequestToNode() {
		try {
			br = new BufferedReader(new FileReader("ClientFile/ClientFile.txt"));
			String hostIp = null;
			int hostPort = 0;
			String line;

			while((line= br.readLine()) != null) {
				String[] tokenizedMessage = line.split(" "); 
				hostIp = tokenizedMessage[2];
				hostPort = Integer.parseInt(tokenizedMessage[3]);
				try {
					socket = new Socket(hostIp, hostPort);
					out = new PrintWriter(socket.getOutputStream(), true);
					out.println(tokenizedMessage[0] + " " + tokenizedMessage[1]);
					System.out.println(tokenizedMessage[0] + " " + tokenizedMessage[1]);
					out.close();
	        	}
	        	catch (Exception e) {
	    	    	System.out.println("Cannot find host " + hostIp + ":" + hostPort);
	    	    }
			}
		} catch (NumberFormatException | IOException e) {
	    	System.out.println("Cannot read file");
		}
		
	}
}
