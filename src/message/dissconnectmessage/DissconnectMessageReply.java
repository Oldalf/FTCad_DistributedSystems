package message.dissconnectmessage;

import java.util.UUID;

import message.MessagePayload;
import message.connectmessage.ConnectMessageReply;


public class DissconnectMessageReply extends DissconnectMessage{
	private static UUID messageUUID = UUID.fromString("77befc38-40f5-11e9-b210-d663bd873d93");
	
	public DissconnectMessageReply() {
		super(DissconnectMessageReply.messageUUID);
	}
	
	protected DissconnectMessageReply(UUID uuid) {
		super(uuid);
	}
	protected DissconnectMessageReply(MessagePayload message) {
		super(message);
	}
//	public DissconnectMessageReply(Replys serverAnswer) {
//		this.reply = serverAnswer;
//	}
	
	
	
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
