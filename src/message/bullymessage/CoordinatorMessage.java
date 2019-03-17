package message.bullymessage;

import java.util.UUID;

import org.jgroups.tests.rt.transports.JGroupsTransport;

import State.FrontendState;
import State.ReplicaManagerState;
import replicaManager.AddressConverter;

public class CoordinatorMessage extends BullyMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9218691209050850699L;
	private static UUID messageUUID = UUID.fromString("1fe2de74-3ff8-11e9-b210-d663bd873d93");

	public CoordinatorMessage() {
		super(CoordinatorMessage.messageUUID);
	}

	public CoordinatorMessage(UUID uuid) {
		super(uuid);
	}

	public CoordinatorMessage(AddressConverter addressC) {
		super(CoordinatorMessage.messageUUID);
		this.addressC = addressC;
	}

	@Override
	public void executeForFrontend(FrontendState state) {
		state.primaryMissing = false;
		org.jgroups.util.UUID JGroupUUID = addressC.PossiblyCreateAndGetJGroupsUUID();
		state.primaryAddress = JGroupUUID;
	}

	private void CoordinatorExecuteForRM(ReplicaManagerState state) {
		state.electionTimeout = null;
		state.onGoingElection = false;
		org.jgroups.util.UUID JGroupUUID = addressC.PossiblyCreateAndGetJGroupsUUID();
		state.primaryAddress = JGroupUUID;
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
