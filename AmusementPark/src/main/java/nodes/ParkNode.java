package nodes;

import communication.*;

public class ParkNode {

	private String id;
	private String ipAddress;
	private String port;
	private int metricValue;
	
	private ParkNodeCommunicator parkNodeCommunicator;

	public ParkNode(String id, String ipAddress, String port, int metricValue) {
		this.id = id;
		this.ipAddress = ipAddress;
		this.port = port;
		this.metricValue = metricValue;
		System.out.print("Node " + id + " ");
		parkNodeCommunicator = new ParkNodeCommunicator(this);
	}

	public String getPort() {
		return this.port;
	}

}
