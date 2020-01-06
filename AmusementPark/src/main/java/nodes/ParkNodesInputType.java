package nodes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ParkNodesInputType {

	ParkNode parkNode;
	private static final String LOCALHOST = "127.0.0.1";
	private static final Object FLEXIBLE_TYPE = "FLEXIBLE";
	
	public ParkNodesInputType(String filename) {
		String id, port;
		int metricValue;	
		
		File file = new File(filename);
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			id = br.readLine();
			if (id.equals(FLEXIBLE_TYPE)) {
				boolean flexible = true;
				id = br.readLine();
			}
			port = br.readLine();
			metricValue = Integer.parseInt(br.readLine());
			parkNode = new ParkNode(id, LOCALHOST, port, metricValue);
			
			String neighbourLine, neighbourId, neighbourIp, neighbourPort;
			String[] tokenizedNeighbourLine;
			while((neighbourLine = br.readLine()) != null) {
				tokenizedNeighbourLine = neighbourLine.split(" ");
				neighbourId = tokenizedNeighbourLine[0];
				neighbourIp = tokenizedNeighbourLine[1];
				neighbourPort = tokenizedNeighbourLine[2];
				NodeToCommunicateWith neighbour = new NodeToCommunicateWith(neighbourId, neighbourIp, neighbourPort);
				parkNode.addPossibleNeighbour(neighbour);
				
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
