package message.connectmessage;

import java.util.UUID;

import State.FrontendState;
import State.ReplicaManagerState;
import message.MessagePayload;
import message.Reply;

public class ConnectMessageReply extends ConnectMessage {
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
