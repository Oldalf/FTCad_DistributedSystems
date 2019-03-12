package message.drawmessage;

import java.util.UUID;

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
	protected void executeForFrontend() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void executeForReplicaManager() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executeForBackupReplicaManager() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executeForPrimaryReplicaManager() {
		// TODO Auto-generated method stub
		
	}

}
