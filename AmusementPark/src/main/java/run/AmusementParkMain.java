package run;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import nodes.ParkNode;
import nodes.ParkNodesInputType;

public class AmusementParkMain {
	public static void main(String[] args) {
		ParkNodesInputType parkNodesInputType = new ParkNodesInputType("Map/Node_J.txt");
		ParkNode parkNode = parkNodesInputType.returnParkNode();
		Scanner scanner = new Scanner(System.in);
		while (true) {
			String input = scanner.nextLine();
			if (input.equals("ELECTION"))
				parkNode.receiveFromConsole(input);
		}
	}
}
