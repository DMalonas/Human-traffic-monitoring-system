package nodes;

public class ParkNode {

	private String id;
	private String ipAddress;
	private String port;
	private int metricValue;

	public ParkNode(String id, String ipAddress, String port, int metricValue) {
		this.id = id;
		this.ipAddress = ipAddress;
		this.port = port;
		this.metricValue = metricValue;
	}

}
