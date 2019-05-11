package message.removedrawmessage;

import java.util.LinkedList;
import java.util.UUID;

import DCAD.GObject;
import Role.FrontendRole;
import Role.ReplicaManagerBackupRole;
import Role.ReplicaManagerPrimaryRole;
import Role.ReplicaManagerRole;
import State.FrontendState;
import State.ReplicaManagerState;
import message.MessagePayload;
import message.Reply;

public class RemoveDrawMessageReply extends RemoveDrawMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static UUID messageUUID = UUID.fromString("1934207e-489f-11e9-8646-d663bd873d93");

	private LinkedList<GObject> state = new LinkedList<GObject>();
	private Reply reply;

	protected RemoveDrawMessageReply(MessagePayload message) {
		super(message);
	}

	protected RemoveDrawMessageReply(UUID uuid) {
		super(uuid);
	}

	public RemoveDrawMessageReply() {
		super(RemoveDrawMessageReply.messageUUID);
	}

	public RemoveDrawMessageReply(LinkedList<GObject> state, Reply reply) {
		super(RemoveDrawMessageReply.messageUUID);
		this.reply = reply;
		this.state = state;
	}

	public void setState(LinkedList<GObject> state) {
		this.state = state;
	}

	public LinkedList<GObject> getState() {
		return this.state;
	}

	public void setReply(Reply reply) {
		this.reply = reply;
	}

	public Reply getReply() {
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
