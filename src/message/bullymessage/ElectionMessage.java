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



	private void CoordinatorExecuteForRM(ReplicaManagerState state) {
		state.callElection = true;
		state.onGoingElection = true;
	}


	@Override
	public void executeForFrontend(FrontendState state) {
		if(state.role instanceof FrontendRole) {
			
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
			state.primaryAddress = null;
			state.onGoingElection = true;
			state.callElection = true;
			state.primaryMissing = true;
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
