package run;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import nodes.ParkNode;
import nodes.ParkNodesInputType;
import utilities.MessageTypes;


/**
 * Derives the node information, and then 
 * sets up a node. Then the node just 
 * endlessly wait for input which is 
 * fetched through a Scanner object.
 * If the input message is ELECTION, 
 * then the node starts an election
 * if it is LEAVE then the node 
 * leaves the network.
 * @param args
 */
public class AmusementParkMain {
	public static void main(String[] args) {
		ParkNodesInputType parkNodesInputType = new ParkNodesInputType("Map/Node_J.txt");
		ParkNode parkNode = parkNodesInputType.returnParkNode();
		Scanner scanner = new Scanner(System.in);
		while (true) {
			String input = scanner.nextLine();
			if (input.equals(MessageTypes.ELECTION_MESSAGE) || input.equals(MessageTypes.LEAVE_MESSAGE)) {
				System.out.println("input is " + input);
				parkNode.receiveFromConsole(input);
			} else {
				System.out.println("Not a valid input. Please enter \"ELECTION\" or \"LEAVE\"");
			}
		}
	}
}
