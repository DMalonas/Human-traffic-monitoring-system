package nodes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import utilities.ParkNodeUtilities;

public class ParkNodesInputType {

	private ParkNode parkNode;
	private static final Object FLEXIBLE_TYPE = "FLEXIBLE";
	private static final String LOCALHOST = "127.0.0.1";

	
	/**
	 * Constructor creates the node.
	 * Possible neighbours are added 
	 * to the possibleNeighbours list.
	 * @param filename The name of the file from which we are reading the node details.
	 */
	public ParkNodesInputType(String filename) {
		String id, port;
		int metricValue;	

		boolean flexible = false;
		File file = new File(filename);
		try {
			BufferedReader br = new BufferedReader(new FileReader(file)); // https://stackoverflow.com/questions/5868369/how-to-read-a-large-text-file-line-by-line-using-java
			id = br.readLine();
			if (id.equals(FLEXIBLE_TYPE)) {
				flexible = true;
				id = br.readLine();
			}
			port = br.readLine();
			metricValue = Integer.parseInt(br.readLine());
			parkNode = new ParkNode(id, LOCALHOST, port, metricValue);		
			String neighbourLine, neighbourId, neighbourIp, neighbourPort;
			String[] tokenizedNeighbourLine;
			while((neighbourLine = br.readLine()) != null) {
				tokenizedNeighbourLine = neighbourLine.split(" "); // https://docs.oracle.com/javase/7/docs/api/java/util/StringTokenizer.html
				neighbourId = tokenizedNeighbourLine[0];
				neighbourIp = tokenizedNeighbourLine[1];
				neighbourPort = tokenizedNeighbourLine[2];
				NodeToCommunicateWith neighbour = new NodeToCommunicateWith(neighbourId, neighbourIp, neighbourPort);
				parkNode.addPossibleNeighbour(neighbour);
				
			}
			if (flexible) {
				ParkNodeUtilities.addNeighborsInFlexibleNetworkFromFile(parkNode);
			}
			br.close();		
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			System.exit(0);
		} catch (IOException e) {
			System.out.println("Cannot read file.");
			System.exit(0);
		} catch (Exception e) {
			System.out.println("Incorrect input format.");
			System.exit(0);
		}
	}

	public ParkNode returnParkNode() {
		return parkNode;
	}

	
}
