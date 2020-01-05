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

	public void processIncomingMessage(String incomingMessage) {
		// TODO Auto-generated method stub
		String arr[] = incomingMessage.split(incomingMessage, 2); // https://stackoverflow.com/questions/5067942/what-is-the-best-way-to-extract-the-first-word-from-a-string-in-java
		if (arr.length == 2) {
			if(arr[0].equals("ENTER") || arr[0].equals("EXIT")) {
				System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
				parentParkNode.receiveFromClient(arr[0], arr[1]);
			} else {
				parentParkNode.receiveFromNode(arr[0], arr[1]);
			}
		}
	}

}
