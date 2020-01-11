package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * 
 * @author 170011408
 *
 */
public class ClientFunctionalityClass {
	private Socket socket;
	private PrintWriter out;
	private BufferedReader stdIn;
	
	public ClientFunctionalityClass() throws IOException {
		sendRequestToNode();
	}
		
	public void sendRequestToNode() {
	   
		String hostIp = null;
		int hostPort = 0;
	    try(BufferedReader br = new BufferedReader(new FileReader("ClientFile/ClientFile.txt"))) {
	        String line = br.readLine();

	        while (line != null) {
	        	String[] tokenizedMessage = line.split(" ");
	        	hostIp = tokenizedMessage[2];
	        	hostPort = Integer.parseInt(tokenizedMessage[3]);
	        	try {
		        	socket = new Socket(hostIp, hostPort);//Integer.parseInt(tokenizedMessage[3])
				    out = new PrintWriter(socket.getOutputStream(), true);
			    	out.println(tokenizedMessage[0] + " " + tokenizedMessage[1]);
			    	out.close();
	        	}
	        	catch (Exception e) {
	    	    	System.out.println("Cannot find host " + hostIp + ":" + hostPort);
	    	    }
	        	line = br.readLine();
	        }
	    }
	    catch (Exception e) {
	    	System.out.println("Cannot read file");
	    }
	}
}
