package nodes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ParkNodesInputType {

	ParkNode parkNode;
	
	public ParkNodesInputType(String filename) {
		String id, port;
		int metricValue;	
		
		File file = new File(filename);
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			id = br.readLine();
			port = br.readLine();
			metricValue = Integer.parseInt(br.readLine());
			parkNode = new ParkNode(id, "127.0.0.1", port, metricValue);
			
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
