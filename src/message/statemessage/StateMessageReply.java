package message.statemessage;

import java.util.UUID;

import State.FrontendState;
import State.ReplicaManagerState;
import message.MessagePayload;

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
