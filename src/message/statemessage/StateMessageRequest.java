package message.statemessage;

import java.util.UUID;

import State.FrontendState;
import State.ReplicaManagerState;
import message.MessagePayload;
import message.drawmessage.DrawMessage;

public class StateMessageRequest extends StateMessage {
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
