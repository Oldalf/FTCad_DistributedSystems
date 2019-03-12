package message.dissconnectmessage;

import java.util.UUID;

import State.FrontendState;
import State.ReplicaManagerState;
import message.MessagePayload;

public class DissconnectMessageRequest extends DissconnectMessage {
	private static UUID messageUUID = UUID.fromString("77beff12-40f5-11e9-b210-d663bd873d93");
	
	public DissconnectMessageRequest() {
		super(DissconnectMessageRequest.messageUUID);
	}
	
	protected DissconnectMessageRequest(UUID uuid) {
		super(uuid);
	}
	protected DissconnectMessageRequest(MessagePayload message) {
		super(message);
	}
	@Override
	public void executeForFrontend(FrontendState state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executeForReplicaManager(ReplicaManagerState state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executeForBackupReplicaManager(ReplicaManagerState state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executeForPrimaryReplicaManager(ReplicaManagerState state) {
		// TODO Auto-generated method stub
		
	}

}
