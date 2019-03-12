package message.connectmessage;

import java.util.UUID;

import State.FrontendState;
import State.ReplicaManagerState;
import message.MessagePayload;
enum Replys{
	OK, //use to indicate for clients that a connection is established 
	CHANGEUUID, // use to indicate to the clients that the UUID already exists as a established connection in the server
	ERROR, //Server error. 
}
public class ConnectMessageReply extends ConnectMessage {
	private static UUID messageUUID = UUID.fromString("fe28ead0-3b38-11e9-b210-d663bd873d93");
	private Replys reply;
	
	public ConnectMessageReply() {
		super(ConnectMessageReply.messageUUID);
	}
	
	protected ConnectMessageReply(UUID uuid) {
		super(uuid);
	}
	protected ConnectMessageReply(MessagePayload message) {
		super(message);
	}
	public ConnectMessageReply(Replys serverAnswer) {
		super(ConnectMessageReply.messageUUID);
		this.reply = serverAnswer;
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
