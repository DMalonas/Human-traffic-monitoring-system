package communication;

import java.util.Arrays;

import nodes.ParkNode;
import utilities.MessageTypes;

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
		String arr[] = incomingMessage.split(" ", 2); // https://stackoverflow.com/questions/5067942/what-is-the-best-way-to-extract-the-first-word-from-a-string-in-java
//		for (int i = 0; i < arr.length; i++) {
//			System.out.println("arr[" + i + "] == " + arr[i]);
//		}
		if (arr.length == 2) {
			if(arr[0].equals(MessageTypes.ENTER_MESSAGE) || arr[0].equals(MessageTypes.EXIT_MESSAGE)) {
				parentParkNode.receiveFromClient(arr[0], arr[1]);
			} else {
				String[] tokenizedMessage = arr[1].split(" ", 3);
				parentParkNode.receiveFromNode(arr[0], tokenizedMessage[0],  tokenizedMessage[1], tokenizedMessage[2]);
			}
		}
	}

}
