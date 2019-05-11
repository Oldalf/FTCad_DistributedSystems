package message.removedrawmessage;

import java.util.UUID;

import State.FrontendState;
import State.ReplicaManagerState;
import message.Message;
import message.MessagePayload;

public class RemoveDrawMessage extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static UUID messageUUID = UUID.fromString("64c97f04-489d-11e9-8646-d663bd873d93");

	protected RemoveDrawMessage(UUID uuid) {
		super(uuid);
	}

	protected RemoveDrawMessage(MessagePayload message) {
		super(message);
	}

	@Override
	public void executeForFrontend(FrontendState state) {

	}

	@Override
	public void executeForReplicaManager(ReplicaManagerState state) {

	}

	@Override
	public void executeForBackupReplicaManager(ReplicaManagerState state) {

	}

	@Override
	public void executeForPrimaryReplicaManager(ReplicaManagerState state) {

	}

}
