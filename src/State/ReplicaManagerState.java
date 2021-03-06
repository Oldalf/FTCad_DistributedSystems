package State;

import java.util.concurrent.LinkedBlockingQueue;

import org.jgroups.Address;
import org.jgroups.View;

import Role.ReplicaManagerRole;
import replicaManager.ReplicaManagerMessageContainer;

public class ReplicaManagerState {
	/*
	 * Singleton variable
	 */
	private static ReplicaManagerState single_instance = null;

	/*
	 * Election state
	 */
	public boolean primaryMissing = true;
	public boolean onGoingElection = false;
	public boolean callElection = false;
	public volatile boolean stateAcquired = false;
	public Address primaryAddress = null;
	public Address frontendAddress = null;
	public View previousView;
	public Long electionTimeout;
	public ReplicaManagerRole role = ReplicaManagerRole.getInstance();
	
	// election related answer messages, who should we send answer messages to.
	public LinkedBlockingQueue<Address> answerMessageReplyList = new LinkedBlockingQueue<>();
	
	public LinkedBlockingQueue<ReplicaManagerMessageContainer> responseList = new LinkedBlockingQueue<ReplicaManagerMessageContainer>();

	public rmReplicableState rpState = rmReplicableState.getInstance();

	protected ReplicaManagerState() {
		
	}

	public static ReplicaManagerState getInstance() {
		if (single_instance == null)
			single_instance = new ReplicaManagerState();
		return single_instance;
	}

}