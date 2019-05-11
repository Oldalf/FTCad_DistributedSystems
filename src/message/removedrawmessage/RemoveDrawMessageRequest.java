package message.removedrawmessage;

import java.util.UUID;


import DCAD.GObject;
import Role.FrontendRole;
import Role.ReplicaManagerBackupRole;
import Role.ReplicaManagerPrimaryRole;
import Role.ReplicaManagerRole;
import State.FrontendState;
import State.ReplicaManagerState;
import message.MessagePayload;

public class RemoveDrawMessageRequest extends RemoveDrawMessage {

	private static final long serialVersionUID = 1L;
	private static UUID messageUUID = UUID.fromString("dfaa8aec-489d-11e9-8646-d663bd873d93");

	public RemoveDrawMessageRequest()
	    {
	        super(RemoveDrawMessageRequest.messageUUID);
	    }

	protected RemoveDrawMessageRequest(UUID uuid) 
	    {
	        super(uuid);
	    }

	protected RemoveDrawMessageRequest(MessagePayload message) 
	    {
	        super(message);
	    }

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

