package communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ParkNodeReceiver implements Runnable{

	private ParkNodeCommunicator parkNodeCommunicator;
	int port;
	ServerSocket serverSocket;
	
	public ParkNodeReceiver(ParkNodeCommunicator parkNodeCommunicator, int port) {
		this.parkNodeCommunicator = parkNodeCommunicator;
		this.port = port;
	}

	public void start() {
		Thread thread = new Thread(this);
		thread.start();
	}

	public void stop() throws IOException {
		this.serverSocket.close();
	}
	
	
	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Listening from port: " + port);
			while(true) {
				Socket socket = serverSocket.accept();
				InputStream is = socket.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				String message = br.readLine();
				System.out.println(message);
				parkNodeCommunicator.processIncomingMessage(message);
			}
		} catch (IOException e) {
			System.out.println("Port " + port + " listening part closed");
			System.exit(0);
		}
	}

}
