package message.bullymessage;

import java.util.UUID;

import State.ReplicaManagerState;

public class CoordinatorMessage extends BullyMessage {

	private static UUID messageUUID = UUID.fromString("1fe2de74-3ff8-11e9-b210-d663bd873d93"); 
	

	public CoordinatorMessage() {
		super(CoordinatorMessage.messageUUID);
	}

	public CoordinatorMessage(UUID uuid) {
		super(uuid);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void executeForFrontend() {
		// TODO Auto-generated method stub

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
