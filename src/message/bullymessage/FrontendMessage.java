package message.bullymessage;

import java.util.UUID;

import org.jgroups.Address;

public class FrontendMessage extends BullyMessage {
	private static UUID messageUUID = UUID.fromString("e9684186-44ca-11e9-b210-d663bd873d93"); 
	private Address m_Address;
	protected FrontendMessage() {
		super(FrontendMessage.messageUUID);
	}

	protected FrontendMessage(UUID uuid) {
		super(uuid);
		// TODO Auto-generated constructor stub
	}
	
	public FrontendMessage(Address adrs) {
		super(FrontendMessage.messageUUID);
		this.m_Address = adrs;
	}
	
	public Address getAddress() {
		return this.m_Address;
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
