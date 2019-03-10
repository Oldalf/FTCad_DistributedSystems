package message.bullymessage;

import java.util.UUID;

import org.jgroups.Address;

import message.MessagePayload;

public class ElectionMessage extends BullyMessage {
	private static UUID messageUUID = UUID.fromString("11dd613e-3f65-11e9-b210-d663bd873d93");
	private String m_id;
	private Address m_address;

	// Used by jackson to create prototype objects.
	public ElectionMessage() {
		super(ElectionMessage.messageUUID);
	}

	public ElectionMessage(UUID uuid) {
		super(uuid);
	}

	@Override
	public void executeForFrontend() {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeForReplicaManager() {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeForBackupReplicaManager() {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeForPrimaryReplicaManager() {
		// TODO Auto-generated method stub

	}

}
