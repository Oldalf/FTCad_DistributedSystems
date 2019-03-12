package message.statemessage;

import java.util.UUID;

import message.MessagePayload;
import message.drawmessage.DrawMessage;

public class StateMessageReply extends StateMessage {
	private static UUID messageUUID = UUID.fromString("1950588a-40f6-11e9-b210-d663bd873d93");

	public StateMessageReply() {
		super(StateMessageReply.messageUUID);
	}
	
	protected StateMessageReply(UUID uuid) {
		super(uuid);
	}
	protected StateMessageReply(MessagePayload message) {
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
