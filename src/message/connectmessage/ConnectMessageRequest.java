package message.connectmessage;

import java.util.UUID;

import Role.ClientRole;
import Role.FrontendRole;
import Role.ReplicaManagerBackupRole;
import Role.ReplicaManagerPrimaryRole;
import Role.ReplicaManagerRole;
import State.FrontendState;
import State.ReplicaManagerState;
import State.clientState;
import message.MessagePayload;

public class ConnectMessageRequest extends ConnectMessage {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static UUID messageUUID = UUID.fromString("e8cf5202-4027-11e9-b210-d663bd873d93");
	private volatile String id;

	public ConnectMessageRequest() {
		super(ConnectMessageRequest.messageUUID);
	}

	protected ConnectMessageRequest(UUID uuid) {
		super(uuid);
	}

	protected ConnectMessageRequest(MessagePayload message) {
		super(message);
	}

	public ConnectMessageRequest(String id) {
		super(ConnectMessageRequest.messageUUID);
		this.id = id;

	}

	public String getID() {
		return id;
	}

	@Override
	public void executeForFrontend(FrontendState state) {
		if(state.role instanceof FrontendRole) {
			
		}else {
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
	@Override
	public void executeForClient(clientState state) {
		if(state.role instanceof ClientRole) {
			
		}
		else {
			throw new IllegalStateException();
		}
		
	}

	
}
