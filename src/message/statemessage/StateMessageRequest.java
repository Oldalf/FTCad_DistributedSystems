package message.statemessage;

import java.util.UUID;

import Role.ClientRole;
import Role.FrontendRole;
import Role.ReplicaManagerBackupRole;
import Role.ReplicaManagerPrimaryRole;
import Role.ReplicaManagerRole;
import State.FrontendState;
import State.ReplicaManagerState;
import State.clientState;
import message.MessagePayload;
import replicaManager.RequestContainer;
import replicaManager.RequestContainer.RequestStage;
import replicaManager.RequestContainer.requestType;


public class StateMessageRequest extends StateMessage {
	private static final long serialVersionUID = 1L;
	private static UUID messageUUID = UUID.fromString("19505a1a-40f6-11e9-b210-d663bd873d93");

	public StateMessageRequest() {
		super(StateMessageRequest.messageUUID);
	}
	
	protected StateMessageRequest(UUID uuid) {
		super(uuid);
	}
	protected StateMessageRequest(MessagePayload message) {
		super(message);
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
//		if(state.role instanceof ReplicaManagerRole) {
//			
//		}
//		else {
//			throw new IllegalStateException();
//		}
		
	}

	@Override
	public void executeForBackupReplicaManager(ReplicaManagerState state) {
		// if(state.role instanceof ReplicaManagerBackupRole) {
		//
		// }
		// else {
		// throw new IllegalStateException();
		// }
		
	}

	@Override
	public void executeForPrimaryReplicaManager(ReplicaManagerState state) {
		//if(state.role instanceof ReplicaManagerPrimaryRole) {
			// create requestContainer with type state (no object needed)
			RequestContainer rq = new RequestContainer(null, requestType.State, RequestStage.ConfrimedByBackup);
			// set requester to the messageSenderUUID.
			rq.setRequester(this.getSenderUUID());
			state.rpState.ReadyToSendRequests.add(rq);
	
//		}
//		else {
//			throw new IllegalStateException();
//		}
		
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
