package nodes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ParkNodesInputType {

	private ParkNode parkNode;
	private final String localhost = "127.0.0.1";
	
	public ParkNodesInputType(String filename) {
		String id, port;
		int metricValue;	
		
		File file = new File(filename);
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			id = br.readLine();
			port = br.readLine();
			metricValue = Integer.parseInt(br.readLine());
			parkNode = new ParkNode(id, localhost, port, metricValue);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
