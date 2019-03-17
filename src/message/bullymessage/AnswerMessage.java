package message.bullymessage;

import java.util.UUID;

import State.FrontendState;
import State.ReplicaManagerState;
import replicaManager.AddressConverter;

public class AnswerMessage extends BullyMessage {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5575648795817322320L;
	private static UUID messageUUID = UUID.fromString("c38de910-3ff8-11e9-b210-d663bd873d93");

	public AnswerMessage() {
		super(AnswerMessage.messageUUID);
	}

	public AnswerMessage(UUID uuid) {
		super(uuid);

	}

	public AnswerMessage(AddressConverter addressC) {
		super(AnswerMessage.messageUUID);
		this.addressC = addressC;
	}

	@Override
	public void executeForFrontend(FrontendState state) {

	}

	private void CoordinatorExecuteForRM(ReplicaManagerState state) {
		state.electionTimeout = null;
	}

	@Override
	public void executeForReplicaManager(ReplicaManagerState state) {
		CoordinatorExecuteForRM(state);
	}

	@Override
	public void executeForBackupReplicaManager(ReplicaManagerState state) {
		CoordinatorExecuteForRM(state);
	}

	@Override
	public void executeForPrimaryReplicaManager(ReplicaManagerState state) {
		try {
			throw new IllegalAccessException("You should not use this execute method from the current class.");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
