package State;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import DCAD.GObject;
import replicaManager.RequestContainer;

public class rmReplicableState implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6253130282053636281L;

	/*
	 * singleton/factory variable
	 */
	private static rmReplicableState single_instance = null;

	/*
	 * Object state
	 */
	public LinkedList<GObject> cadState = new LinkedList<GObject>();

	/*
	 * Request state
	 */
	public LinkedList<RequestContainer> requestState = new LinkedList<RequestContainer>();
	
	// All requests added here, once time is X ahead of current time they will be removed
	public ConcurrentHashMap<GObject, RequestContainer> Object2Request_state = new ConcurrentHashMap<GObject, RequestContainer>();
	// All requests that have been confirmed by the other replicaManagers go here and then get sent to the frontEnd.
	public LinkedBlockingQueue<RequestContainer> ReadyToSendRequests = new LinkedBlockingQueue<>();
	
	protected rmReplicableState() { 

	}

	public static rmReplicableState getInstance() {
		if (single_instance == null)
			single_instance = new rmReplicableState();
		return single_instance;
	}
	
}
