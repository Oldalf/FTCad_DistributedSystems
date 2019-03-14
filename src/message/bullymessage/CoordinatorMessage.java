package message.bullymessage;

import java.util.UUID;

import org.jgroups.Address;

import State.FrontendState;
import State.ReplicaManagerState;

public class CoordinatorMessage extends BullyMessage {

	private static UUID messageUUID = UUID.fromString("1fe2de74-3ff8-11e9-b210-d663bd873d93");
	private Address m_address;

	public CoordinatorMessage() {
		super(CoordinatorMessage.messageUUID);
	}

	protected CoordinatorMessage(UUID uuid) {
		super(uuid);
		// TODO Auto-generated constructor stub
	}

	public CoordinatorMessage(Address m_address) {
		super(CoordinatorMessage.messageUUID);
		this.m_address = m_address;
	}

	@Override
	public void executeForFrontend(FrontendState state) {
		state.primaryMissing = false;
		state.primaryAddress = this.m_address;

	}

	private void CoordinatorExecuteForRM(ReplicaManagerState state) {
		state.electionTimeout = null;
		state.onGoingElection = false;
		state.primaryAddress = m_address;
		System.out.println("new primary is!: " + m_address);
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
		// Someone think they have a higher id, yielding primary.
		CoordinatorExecuteForRM(state);

	}

}
