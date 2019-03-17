package message.frontendmessage;

import java.util.UUID;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import org.jgroups.Address;

import Role.FrontendRole;
import Role.ReplicaManagerBackupRole;
import Role.ReplicaManagerPrimaryRole;
import Role.ReplicaManagerRole;
import State.FrontendState;
import State.ReplicaManagerState;
import message.Message;

public class FrontendMessage extends Message {

	private static UUID messageUUID = UUID.fromString("e9684186-44ca-11e9-b210-d663bd873d93"); 
	private Address address;
	protected FrontendMessage() {
		super(FrontendMessage.messageUUID);
	}

	protected FrontendMessage(UUID uuid) {
		super(uuid);
		// TODO Auto-generated constructor stub
	}
	
	public FrontendMessage(Address address) {
		super(FrontendMessage.messageUUID);
		this.address = address;
	}
	
	public Address getAddress() {
		return this.address;
	}

	@Override
	public void executeForFrontend(FrontendState state) {
		if(state.role instanceof FrontendRole) {
			
		}
		else {
			throw new IllegalStateException();
		}
		
	}

	@Override
	public void executeForReplicaManager(ReplicaManagerState state) {
		if(state.role instanceof ReplicaManagerRole) {
			
		}
		else {
			throw new IllegalStateException();
		}
		
	}

	@Override
	public void executeForBackupReplicaManager(ReplicaManagerState state) {
		if(state.role instanceof ReplicaManagerBackupRole) {
			
		}
		else {
			throw new IllegalStateException();
		}
		
	}

	@Override
	public void executeForPrimaryReplicaManager(ReplicaManagerState state) {
		if(state.role instanceof ReplicaManagerPrimaryRole) {
			
		}
		else {
			throw new IllegalStateException();
		}
		
	}

}
