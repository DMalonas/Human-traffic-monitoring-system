package communication;

import java.util.Arrays;

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

	public void processIncomingMessage(String message) {
		// TODO Auto-generated method stub
		String[] arr = message.split(message, 2);
		System.out.println(Arrays.toString(arr));
	}

}
