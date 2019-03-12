package message.bullymessage;

import java.util.UUID;

import org.jgroups.Address;

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
	public ElectionMessage(Address address, String id) {
		super(ElectionMessage.messageUUID);
	}
	@Override
	protected void executeForFrontend() {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void executeForReplicaManager() {
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
