package message.bullymessage;

import java.util.UUID;

import org.jgroups.Address;

import State.FrontendState;
import State.ReplicaManagerState;

public class ElectionMessage extends BullyMessage {

	private static UUID messageUUID = UUID.fromString("11dd613e-3f65-11e9-b210-d663bd873d93");
	private Address m_address;

	// Used by jackson to create prototype objects.
	protected ElectionMessage() {
		super(ElectionMessage.messageUUID);
	}

	protected ElectionMessage(UUID uuid) {
		super(uuid);
	}

	// Use this constructor
	public ElectionMessage(Address address) {
		super(ElectionMessage.messageUUID);
		this.m_address = address;
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
