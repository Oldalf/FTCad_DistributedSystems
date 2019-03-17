package State;

import java.io.Serializable;
import java.util.LinkedList;

import DCAD.GObject;
import replicaManager.RequestContainer;

public class rmReplicableState implements Serializable{
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

	protected rmReplicableState() { 

	}

	public static rmReplicableState getInstance() {
		if (single_instance == null)
			single_instance = new rmReplicableState();
		return single_instance;
	}
}
