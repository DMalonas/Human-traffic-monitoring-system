package communication;

import nodes.ParkNode;

public class ParkNodeCommunicator {

	private int port;
	private ParkNode parentParkNode;
	private ParkNodeReceiver parkNodeReceiver;

	public ParkNodeCommunicator(ParkNode parentParkNode) {
		this.port = Integer.parseInt(parentParkNode.getPort());
		this.parentParkNode = parentParkNode;
		parkNodeReceiver = new ParkNodeReceiver(this, port);
		parkNodeReceiver.start();
	}

}
