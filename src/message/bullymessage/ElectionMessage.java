package message.bullymessage;

import java.util.UUID;

import State.FrontendState;
import State.ReplicaManagerState;
import replicaManager.AddressConverter;

public class ElectionMessage extends BullyMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3089389173415240163L;
	private static UUID messageUUID = UUID.fromString("11dd613e-3f65-11e9-b210-d663bd873d93");
	// Used by jackson to create prototype objects.
	public ElectionMessage() {
		super(ElectionMessage.messageUUID);
	}

	public ElectionMessage(UUID uuid) {
		super(uuid);
	}

	// Use this constructor
	public ElectionMessage(AddressConverter addressC) {
		super(ElectionMessage.messageUUID);
		this.addressC = addressC;
	}

	@Override
	public void executeForFrontend(FrontendState state) {
		// TODO Auto-generated method stub

	}

	private void CoordinatorExecuteForRM(ReplicaManagerState state) {
		state.callElection = true;
		state.onGoingElection = true;
	}

	@Override
	public void executeForReplicaManager(ReplicaManagerState state) {
		CoordinatorExecuteForRM(state);
	}

	@Override
	public void executeForBackupReplicaManager(ReplicaManagerState state) {
		CoordinatorExecuteForRM(state);
	}

	@Override
	public void executeForPrimaryReplicaManager(ReplicaManagerState state) {
		state.primaryAddress = null;
		state.onGoingElection = true;
		state.callElection = true;
		state.primaryMissing = true;
		/*
		 * TODO send answer message to rm that send electionMessage.
		 */

	}

}
