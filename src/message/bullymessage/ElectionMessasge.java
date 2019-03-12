package message.bullymessage;

import java.util.UUID;

import org.jgroups.Address;

import message.MessagePayload;

public class ElectionMessasge extends BullyMessage {
	private static UUID messageUUID = UUID.fromString("11dd613e-3f65-11e9-b210-d663bd873d93"); 
	private String m_id; 
	private Address m_address;
	
	
	//Used by jackson to create prototype objects.
	public ElectionMessasge() {
		super(ElectionMessasge.messageUUID);
	}
	protected ElectionMessasge(UUID uuid) {
		super(uuid);
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
