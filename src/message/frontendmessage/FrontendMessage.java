package message.frontendmessage;

import java.util.UUID;

import org.jgroups.Address;

import State.FrontendState;
import State.ReplicaManagerState;
import message.Message;

public class FrontendMessage extends Message {

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
	public void executeForFrontend(FrontendState state) {
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