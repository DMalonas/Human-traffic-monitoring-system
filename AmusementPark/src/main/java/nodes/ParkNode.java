package nodes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.math3.ml.neuralnet.sofm.NeighbourhoodSizeFunction;

import communication.*;

public class ParkNode {

	private String id;
	private String ipAddress;
	private String port;
	private int metricValue;
	
	private ParkNodeCommunicator parkNodeCommunicator;
	private ArrayList<Neighbour> neighbours;

	public ParkNode(String id, String ipAddress, String port, int metricValue) {
		this.id = id;
		this.ipAddress = ipAddress;
		this.port = port;
		this.metricValue = metricValue;
		System.out.print("Node " + id + " ");
		parkNodeCommunicator = new ParkNodeCommunicator(this);
		neighbours = new ArrayList<Neighbour>();
	}

	public String getPort() {
		return port;
	}

	public void receiveFromClient(String messageType, String ticketID) {
		if(messageType.equals("ENTER")) {
			printToConsole(messageType + " REQUEST BY " + ticketID);
		}
		if(messageType.equals("EXIT")) {
			printToConsole(messageType + " REQUEST BY " + ticketID);
		}
	}

	private void printToConsole(String message) {
		System.out.println("[" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()) + " | " + id + " ] " + message);
	}

	public void receiveFromNode(String string, String string2) {
		
	}

	public void receiveFromConsole(String input) {
		
	}

	public void addNeighbour(Neighbour neighbour) {
		neighbours.add(neighbour);
	}

}
