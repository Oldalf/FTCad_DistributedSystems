package message.drawmessage;

import java.util.UUID;

import State.FrontendState;
import State.ReplicaManagerState;
import message.MessagePayload;

public class DrawMessageReply extends DrawMessage {
	private static UUID messageUUID = UUID.fromString("77bf05fc-40f5-11e9-b210-d663bd873d93");
	
	public DrawMessageReply() {
		super(DrawMessageReply.messageUUID);
	}
	
	protected DrawMessageReply(UUID uuid) {
		super(uuid);
	}
	protected DrawMessageReply(MessagePayload message) {
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
