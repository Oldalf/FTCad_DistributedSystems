package message.statemessage;

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
import message.MessagePayload;

public class StateMessageReply extends StateMessage {
	private static final long serialVersionUID = 1L;
	private static UUID messageUUID = UUID.fromString("1950588a-40f6-11e9-b210-d663bd873d93");
	private LinkedList<GObject> cadState = new LinkedList<GObject>();

	public StateMessageReply() {
		super(StateMessageReply.messageUUID);
	}

	protected StateMessageReply(UUID uuid) {
		super(uuid);
	}
	protected StateMessageReply(MessagePayload message) {
		super(message);
	}

	public void setState(LinkedList<GObject> cadState) {
		this.cadState = cadState;
	}

	@Override
	public void executeForFrontend(FrontendState state) {
		if(state.role instanceof FrontendRole) {
			state.connectedClients.get(this.getReceiverUUID()).getMessageQueue().add(this);
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

		}
		else {
			throw new IllegalStateException();
		}

	}

}
