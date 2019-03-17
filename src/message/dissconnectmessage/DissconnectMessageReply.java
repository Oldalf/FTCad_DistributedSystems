package message.dissconnectmessage;

import java.util.UUID;

import Role.FrontendRole;
import Role.ReplicaManagerBackupRole;
import Role.ReplicaManagerPrimaryRole;
import Role.ReplicaManagerRole;
import State.FrontendState;
import State.ReplicaManagerState;
import message.MessagePayload;


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
	public void executeForFrontend(FrontendState state) {
		if(state.role instanceof FrontendRole) {
			
		}
		else {
			throw new IllegalStateException();
		}
		
	}

	@Override
	public void executeForReplicaManager(ReplicaManagerState state) {
		if(state.role instanceof ReplicaManagerRole) {
			
		}
		else {
			throw new IllegalStateException();
		}
		
	}

	@Override
	public void executeForBackupReplicaManager(ReplicaManagerState state) {
		if(state.role instanceof ReplicaManagerBackupRole) {
			
		}
		else {
			throw new IllegalStateException();
		}
		
	}

	@Override
	public void executeForPrimaryReplicaManager(ReplicaManagerState state) {
		if(state.role instanceof ReplicaManagerPrimaryRole) {
			
		}
		else {
			throw new IllegalStateException();
		}
		
	}

}
