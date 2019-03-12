package message.bullymessage;

import java.util.UUID;

import org.jgroups.Address;

import State.ReplicaManagerState;
import message.MessagePayload;

public class ElectionMessage extends BullyMessage {

	private static UUID messageUUID = UUID.fromString("11dd613e-3f65-11e9-b210-d663bd873d93"); 
	private String m_id; 
	private Address m_address;
	
	
	//Used by jackson to create prototype objects.
	protected ElectionMessage() {
		super(ElectionMessage.messageUUID);
	}
	protected ElectionMessage(UUID uuid) {
		super(uuid);
	}
	//Use this constructor 
	public ElectionMessage(Address address) {
		super(ElectionMessage.messageUUID);
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
