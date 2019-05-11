package message.connectmessage;

import java.util.UUID;

import Role.FrontendRole;
import Role.ReplicaManagerBackupRole;
import Role.ReplicaManagerPrimaryRole;
import Role.ReplicaManagerRole;
import State.FrontendState;
import State.ReplicaManagerState;
import message.MessagePayload;
import message.Reply;

public class ConnectMessageReply extends ConnectMessage {

	private static final long serialVersionUID = 1L;
	private static UUID messageUUID = UUID.fromString("fe28ead0-3b38-11e9-b210-d663bd873d93");
	private Reply reply;
	
	public ConnectMessageReply() {
		super(ConnectMessageReply.messageUUID);
	}
	
	protected ConnectMessageReply(UUID uuid) {
		super(uuid);
	}
	protected ConnectMessageReply(MessagePayload message) {
		super(message);
	}
	public ConnectMessageReply(Reply reply) {
		super(ConnectMessageReply.messageUUID);
		this.reply = reply;
	}
	
	public Reply getReply()
	{
		return this.reply;
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
