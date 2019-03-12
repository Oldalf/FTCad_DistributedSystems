package message.bullymessage;

import java.util.UUID;

import State.ReplicaManagerState;

public class AnswerMessage extends BullyMessage {
	private static UUID messageUUID = UUID.fromString("c38de910-3ff8-11e9-b210-d663bd873d93");

	public AnswerMessage() {
		super(AnswerMessage.messageUUID);
	}

	public AnswerMessage(UUID uuid) {
		super(uuid);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void executeForFrontend() {

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
