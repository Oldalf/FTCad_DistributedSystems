package message.bullymessage;

import java.util.UUID;

import Role.FrontendRole;
import Role.ReplicaManagerBackupRole;
import Role.ReplicaManagerPrimaryRole;
import Role.ReplicaManagerRole;
import State.FrontendState;
import State.ReplicaManagerState;
import replicaManager.AddressConverter;

public class AnswerMessage extends BullyMessage {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5575648795817322320L;
	private static UUID messageUUID = UUID.fromString("c38de910-3ff8-11e9-b210-d663bd873d93");

	public AnswerMessage() {
		super(AnswerMessage.messageUUID);
	}

	public AnswerMessage(UUID uuid) {
		super(uuid);

	}

	public AnswerMessage(AddressConverter addressC) {
		super(AnswerMessage.messageUUID);
		this.addressC = addressC;
	}


	private void CoordinatorExecuteForRM(ReplicaManagerState state) {
		state.electionTimeout = null;
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
			
		}
		else {
			throw new IllegalStateException();
		}
		
	}

}
