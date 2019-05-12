package message.removedrawmessage;

import java.security.KeyStore.Entry;
import java.util.LinkedList;
import java.util.UUID;

import DCAD.GObject;
import Role.ClientRole;
import Role.FrontendRole;
import Role.ReplicaManagerBackupRole;
import Role.ReplicaManagerPrimaryRole;
import Role.ReplicaManagerRole;
import State.FrontendState;
import State.ReplicaManagerState;
import State.clientState;
import frontend.ClientConnection;
import message.MessagePayload;
import message.Reply;

public class RemoveDrawMessageReply extends RemoveDrawMessage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static UUID messageUUID = UUID.fromString("1934207e-489f-11e9-8646-d663bd873d93");
	
	private LinkedList<GObject> newObjectState;
	private Reply reply;

	protected RemoveDrawMessageReply(MessagePayload message) 
	{
		super(message);
	}
	
	protected RemoveDrawMessageReply(UUID uuid) 
	{
		super(uuid);
	}
	
	public RemoveDrawMessageReply()
	{
		super(RemoveDrawMessageReply.messageUUID);
	}
	
	public RemoveDrawMessageReply(LinkedList<GObject> newObjectState, Reply reply)
	{
		super(RemoveDrawMessageReply.messageUUID);
		this.reply = reply;
		this.newObjectState = newObjectState;
	}
	
	public void setObject(LinkedList<GObject>  newObjectState)
	{
		this.newObjectState = newObjectState;
	}
	
	public LinkedList<GObject> getObject()
	{
		return this.newObjectState;
	}
	
	public void setReply(Reply reply)
	{
		this.reply = reply;
	}
	
	public Reply getReply()
	{
		return this.reply;
	}
	@Override
	public void executeForFrontend(FrontendState state) {
		if(state.role instanceof FrontendRole) {
			for(ClientConnection cc: state.connectedClients.values()) {
				cc.getMessageQueue().add(this);
			}
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
	@Override
	public void executeForClient(clientState state) {
		if(state.role instanceof ClientRole) {
			state.globalObjectList = this.newObjectState;
		}
		else {
			throw new IllegalStateException();
		}
		
	}

}
