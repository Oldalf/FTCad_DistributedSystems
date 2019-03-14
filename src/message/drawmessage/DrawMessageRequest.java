package message.drawmessage;

import java.util.UUID;

import State.FrontendState;
import State.ReplicaManagerState;
import message.MessagePayload;

public class DrawMessageRequest extends DrawMessage {
	private static UUID messageUUID = UUID.fromString("77bf00a2-40f5-11e9-b210-d663bd873d93");
	
	public DrawMessageRequest() {
		super(DrawMessageRequest.messageUUID);
	}
	
	protected DrawMessageRequest(UUID uuid) {
		super(uuid);
	}
	protected DrawMessageRequest(MessagePayload message) {
		super(message);
	}
	@Override
	public void executeForFrontend(FrontendState state) {
		
		
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
