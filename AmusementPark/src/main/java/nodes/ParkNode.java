package nodes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Queue;

import org.apache.commons.math3.ml.neuralnet.sofm.NeighbourhoodSizeFunction;

import communication.*;
import utilities.MessageTypes;

public class ParkNode {
	// 1. Identification attributes
	private String id;                                    // Node id
	private String ipAddress;                             // Node ip address
	private String port;                                  // Node's port
	private int metricValue;                              // Node's metric value (the node with the highest one wins the election).
	private ParkNodeCommunicator parkNodeCommunicator;    // The socket communication is managed by the ParkNodeCommunication class.

	// 2. Current coordinator info
	private String currentCoordinatorId;                  // The id of the current coordinator.
	private String currentCoordinatorIp;                  // The ip of the current coordinator.
	private String currentCoordinatorPort;                  // The port of the current coordinator.
	
	// 3. Election related attributes
	private String currentElectionParentId;               // The parent's id for the current election. The election initialiser has no parent.
	private int neighBoursReplied;                         // Number of neighbour nodes that replied.
	private String maxMetricMessage;                      // The message containing the highest metric value, now.
 	private int currentMaxMetric;                         // The current max metric value that a node knows about.
	private String currentEligibleCoordinatorId;            // The current eligible/possible coordinator that the node knows about - election not over yet.
	private String currentEligibleCoordinatorIp;            // The current eligible/possible coordinator's IP that the node knows about - election not over yet.
	private String currentEligibleCoordinatorPort;            // The current eligible/possible coordinator's port that the node knows about - election not over yet.
	private boolean startedElection;                      // True if the node is the one that initiated the election.
	private boolean expectingCoordinatorInfo;             // True if the node is expecting message regarding who is the coordinator
	private boolean isDesignatedElectionStarter;
		
	// 5. Park operation and mutual exclusion related attributes
	private boolean pendingCoordinatorReply;
	private String ticketIdToEnter;
	private String ticketIdToExit;
	
	//6.  Neighbour nodes related attributes
	private ArrayList<NodeToCommunicateWith> neighbours;               // A node's NodeToCommunicateWith objects are stored within the neighbours, ArrayList.
	private ArrayList<NodeToCommunicateWith> possibleNeighbours;// Possible neighbours are the ones that derive from node's input. After validating they are alive, they become neighbours
	
	//7. Being a Coordinator related attributes
	private boolean isCoordinator;                        // True if the node is the current coordinator.
	private Queue<NodeToCommunicateWith> queueOfRequestingNodes;
	private NodeToCommunicateWith currentResourceUtilizingNode;
	private NodeToCommunicateWith designatedElectionStarter;
	
	
	
	
	
	public ParkNode(String id, String ipAddress, String port, int metricValue) {
		
		this.id = id;
		this.ipAddress = ipAddress;
		this.port = port;
		this.metricValue = metricValue;
		System.out.print("Node " + id + " ");

		
		parkNodeCommunicator = new ParkNodeCommunicator(this);
		currentMaxMetric = metricValue;
		maxMetricMessage =  "[" + id + ", " + metricValue + ", " + ipAddress + ", " + port + "]";
		possibleNeighbours = new ArrayList<NodeToCommunicateWith>();
		currentEligibleCoordinatorId = id;
		currentEligibleCoordinatorIp = ipAddress;
		currentEligibleCoordinatorPort = port;
		
		initializeGenericAttributes();
		
	}
	
	
	/**
	 * Initialise attributes with false and null.
	 */
	public void initializeGenericAttributes() {
		// 2. Current coordinator info
		currentCoordinatorId = null;
		currentCoordinatorIp = null;
		currentCoordinatorPort = null;
		isCoordinator = false;  // No node is a coordinator when they are first created
		
		
		//6.  Neighbour nodes related attributes
		neighbours = new ArrayList<NodeToCommunicateWith>();
		possibleNeighbours = new ArrayList<NodeToCommunicateWith>();
		
		
		// 3. Election related attributes
		currentElectionParentId = null;
		neighBoursReplied = 0;
		startedElection = false;
		expectingCoordinatorInfo = true;
		isDesignatedElectionStarter = false;
		
		
		// 5. Park operation and mutual exclusion related attributes
		pendingCoordinatorReply = false;
		ticketIdToEnter = null;
		ticketIdToExit = null;
	}

	public String getPort() {
		return port;
	}

	
	public void receiveFromClient(String messageType, String ticketID) {
		if(!pendingCoordinatorReply && (messageType.equals(MessageTypes.ENTER_MESSAGE) || messageType.equals(MessageTypes.EXIT_MESSAGE))) {
			pendingCoordinatorReply = true;
			if(messageType.equals(MessageTypes.ENTER_MESSAGE)) {
				ticketIdToEnter = ticketID;
			}
			else {
				ticketIdToExit = ticketID;
			}
			printToConsole(messageType + " REQUEST BY " + ticketID);
			sendRequestToCoordinator();
		} else { 
			printToConsole(messageType + " REQUEST BY " + ticketID + ": BAD REQUEST OR NODE BUSY ON ANOTHER REQUEST");
		}
	}

	
	/**
	 * Send a request to the coordinator. 
	 * First check if I am the coordinator.
	 * If not the send "ENTER/EXIT REQUEST BY" + ticketID
	 */
	private void sendRequestToCoordinator() {
		pendingCoordinatorReply = true;
		if (currentCoordinatorId == null) {
			printToConsole(MessageTypes.ENTER_DENIED_NO_COORDINATOR_MESSAGE);
			pendingCoordinatorReply = false;
		} else if (isCoordinator) {
			handleMessageAsCoordinator(id, MessageTypes.MYSELF_MESSAGE, MessageTypes.MYSELF_MESSAGE, MessageTypes.REQUEST_RESOURCE_ACCESS_MESSAGE);
		}
	}
	

	private void handleMessageAsCoordinator(String id2, String myselfMessage, String myselfMessage2,
			String requestResourceAccessMessage) {
		
	}


	private void printToConsole(String message) {
		System.out.println("[" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()) + " | " + id + " ] " + message);
	}

	public void receiveFromNode(String parkNodeId, String parkNodeIp, String parkNodePort, String message) {
		printToConsole("Receive from " + parkNodeId + ": " + message);
		
		// If the message is NEW_NEIGHBOUR ("Flexible" network scenario)
		if (message.equals(MessageTypes.NEW_NEIGHBOUR_MESSAGE)) {
			handleNewNeighbourMessage(parkNodeId, parkNodeIp, parkNodePort);
		}
		// If the neighbour has answered our NEW_NEIGHBOUR_MESSAGE with WELCOME_MESSAGE
		else if (message.equals(MessageTypes.WELCOME_MESSAGE)) {
			handleWelcomeMessage(parkNodeId, parkNodeIp, parkNodePort);
		} 
		//If a node wants to request access from the coordinator or let it know that it will be releasing the resource.
		else if (message.equals(MessageTypes.REQUEST_RESOURCE_ACCESS_MESSAGE) || message.equals(MessageTypes.RELEASING_RESOURCE_MESSAGE)) {
			handleMessageAsCoordinator(parkNodeId, parkNodeIp, parkNodePort, message);
		}
		// If the message is ELECTION
		else if (message.equals(MessageTypes.ELECTION_MESSAGE)) {
			handleElectionMessage(parkNodeId);
		}
		// If the message is ELECTION-ACK
		else if (message.equals(MessageTypes.ELECTION_ACK_MESSAGE)) {
			handleElectionAckMessage();
		}
		//If the messsage is about a neighbour node that left the network.
		else if (message.equals(MessageTypes.NODE_LEAVING_MESSAGE)) {
			handleNodeLeavingMessage(parkNodeId);
		}
		//If the message is about the coordinator that left.
		else if (message.equals(MessageTypes.COORDINATOR_LEFT_MESSAGE)) {
			handleCoordinatorLeftMessage(parkNodeId);
		}
		/* If the message is from the coordinator making this ParkNode the designated election starter node 
		   that will start the election if the coordinator leaves or gets terminated. 
		*/
		else if (message.equals(MessageTypes.DESIGNATED_ELECTION_STARTER_MESSAGE)) {
			isDesignatedElectionStarter = true;
		}
		
		/* If the message is not ELECTION, or ELECTION-ACK, or any of the above,
		 * that means that one of the children sends a message with metric info
		 * to its parent,or that we are dealing with a COORDINATOR message.
		 */
		else {
			String[] tokenizedMessage = message.split(" ");  // split the message.
			//One of the children has replied with metric value info.
			if (tokenizedMessage[0].equals(MessageTypes.ELECTION_ACK_MESSAGE)) {
				handleMetricReceivedMessage(tokenizedMessage[1]); //the last word of tokenizedMessage[1] contains the metric.
			}
			//Handling the new coordinator message. (NEW_COORDINATOR_MESSAGE id ip port) 
			else if (tokenizedMessage[0].equals(MessageTypes.NEW_COORDINATOR_MESSAGE)) {
				handleNewCoordinatorMessage(parkNodeId, tokenizedMessage);
			}
			//Handling the existing coordinator message to the new "flexible nodes".
			else if (tokenizedMessage[0].equals(MessageTypes.EXISTING_COORDINATOR_MESSAGE)) {
				handleExistingCoordinatorMessage(parkNodeId, parkNodeIp, parkNodePort, tokenizedMessage);
			}
			//When a ParkNode gets access to the common resource, Resource.txt.
			else if (tokenizedMessage[0].equals(MessageTypes.GRANTED_RESOURCE_ACCESS_MESSAGE)) {
				handleGrantedResourceAccessMessage(parkNodeId);
			}
			//When a ParkNode has been denied access by the coordinator to the common resource Resource.txt.
			else if (tokenizedMessage[0].equals(MessageTypes.DENIED_RESOURCE_ACCESS_MESSAGE)) {
				handleDeniedResourceAccessMessage(parkNodeId);
			}
		}
	}




	private void handleDeniedResourceAccessMessage(String parkNodeId) {
		// TODO Auto-generated method stub
		
	}


	private void handleGrantedResourceAccessMessage(String parkNodeId) {
		// TODO Auto-generated method stub
		
	}


	private void handleExistingCoordinatorMessage(String parkNodeId, String parkNodeIp, String parkNodePort,
			String[] tokenizedMessage) {
		// TODO Auto-generated method stub
		
	}


	private void handleNewCoordinatorMessage(String parkNodeId, String[] tokenizedMessage) {
		// TODO Auto-generated method stub
		
	}


	private void handleMetricReceivedMessage(String string) {
		// TODO Auto-generated method stub
		
	}


	private void handleCoordinatorLeftMessage(String parkNodeId) {
		// TODO Auto-generated method stub
		
	}


	private void handleNodeLeavingMessage(String parkNodeId) {
		// TODO Auto-generated method stub
		
	}


	private void handleElectionAckMessage() {
		// TODO Auto-generated method stub
		
	}


	private void handleElectionMessage(String parkNodeId) {
		// TODO Auto-generated method stub
		
	}


	private void handleWelcomeMessage(String parkNodeId, String parkNodeIp, String parkNodePort) {
		// TODO Auto-generated method stub
		
	}


	private void handleNewNeighbourMessage(String parkNodeId, String parkNodeIp, String parkNodePort) {
		// TODO Auto-generated method stub
		
	}


	/**
	 * Prints the message received from the console.
	 * A user can start an ELECTION by typing ELECTION
	 * to the console of one of the running nodes, or
	 * terminate the node by typing LEAVE in the console.
	 * @param message The message entered. Valid messages ELECTION, LEAVE.
	 */
	public void receiveFromConsole(String message) {
		printToConsole("Received from Console: " + message);
		if (message.equals(MessageTypes.ELECTION_MESSAGE)) {
			startedElection = true;
			//broadcastToArrayListNodes(message, neighbours, null); //broadcast ELECTION_MESSAGE message to neighbours.
		}
		else if (message.equals(MessageTypes.LEAVE_MESSAGE))
			leaveNetwork();
	}

	private void leaveNetwork() {
		//broadcastToArrayListNodes(MessageTypes.NODE_LEAVING_MESSAGE, neighbours, null);
		printToConsole("LEAVING...");
		System.exit(0);		
	}


	public void addPossibleNeighbour(NodeToCommunicateWith neighbour) {
		possibleNeighbours.add(neighbour);
	}

}
