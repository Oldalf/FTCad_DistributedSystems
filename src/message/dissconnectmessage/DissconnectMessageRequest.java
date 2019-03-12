package message.dissconnectmessage;

import java.util.UUID;

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
