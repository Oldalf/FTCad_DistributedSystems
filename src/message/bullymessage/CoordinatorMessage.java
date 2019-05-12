package message.bullymessage;

import java.util.UUID;

import Role.ClientRole;
import Role.FrontendRole;
import Role.ReplicaManagerBackupRole;
import Role.ReplicaManagerPrimaryRole;
import Role.ReplicaManagerRole;
import State.FrontendState;
import State.ReplicaManagerState;
import State.clientState;
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



	private void CoordinatorExecuteForRM(ReplicaManagerState state) {
		state.electionTimeout = null;
		state.onGoingElection = false;
		org.jgroups.util.UUID JGroupUUID = addressC.PossiblyCreateAndGetJGroupsUUID();
		state.primaryAddress = JGroupUUID;
	}

	
	@Override
	public void executeForFrontend(FrontendState state) {
		if(state.role instanceof FrontendRole) {
			state.primaryMissing = false;
			org.jgroups.util.UUID JGroupUUID = addressC.PossiblyCreateAndGetJGroupsUUID();
			state.primaryAddress = JGroupUUID;
		}
		else {
			throw new IllegalStateException();
		}
		
	}

	@Override
	public void executeForReplicaManager(ReplicaManagerState state) {
		if(state.role instanceof ReplicaManagerRole) {
			CoordinatorExecuteForRM(state);
		}
		else {
			throw new IllegalStateException();
		}
		
	}

	@Override
	public void executeForBackupReplicaManager(ReplicaManagerState state) {
		if(state.role instanceof ReplicaManagerBackupRole) {
			CoordinatorExecuteForRM(state);
		}
		else {
			throw new IllegalStateException();
		}
		
	}

	@Override
	public void executeForPrimaryReplicaManager(ReplicaManagerState state) {
		if(state.role instanceof ReplicaManagerPrimaryRole) {
			CoordinatorExecuteForRM(state);
		}
		else {
			throw new IllegalStateException();
		}
		
	}
	@Override
	public void executeForClient(clientState state) {
		if(state.role instanceof ClientRole) {
			
		}
		else {
			throw new IllegalStateException();
		}
		
	}

	

}
