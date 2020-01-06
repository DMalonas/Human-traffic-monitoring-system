package nodes;

public class Neighbour {

	private String id;
	private String ipAddress;
	private String port;
	
	public Neighbour(String neighbourId, String neighbourIp, String neighbourPort) {
		this.id = neighbourId;
		this.ipAddress = neighbourIp;
		this.port = neighbourPort;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

}
