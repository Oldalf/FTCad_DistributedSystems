package message.connectmessage;

import java.util.UUID;


import State.FrontendState;
import State.ReplicaManagerState;
import message.MessagePayload;

public class ConnectMessageRequest extends ConnectMessage {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static UUID messageUUID = UUID.fromString("e8cf5202-4027-11e9-b210-d663bd873d93");
	private volatile String id;
	
	public ConnectMessageRequest() {
		super(ConnectMessageRequest.messageUUID);
	}
	
	protected ConnectMessageRequest(UUID uuid) {
		super(uuid);
	}
	protected ConnectMessageRequest(MessagePayload message) {
		super(message);
	}
	
	public ConnectMessageRequest(String id) {
		super(ConnectMessageRequest.messageUUID);
		this.id = id;
		
	}
	
	
	public String getID()
	{
		return id;
	}
	
	
	@Override
	public void executeForFrontend(FrontendState state) {
		// TODO Auto-generated method stub
			try {
				throw new IllegalAccessException();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

	@Override
	public void executeForReplicaManager(ReplicaManagerState state) {
		// TODO Auto-generated method stub
		try {
			throw new IllegalAccessException();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void executeForBackupReplicaManager(ReplicaManagerState state) {
		// TODO Auto-generated method stub
		try {
			throw new IllegalAccessException();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void executeForPrimaryReplicaManager(ReplicaManagerState state) {
		// TODO Auto-generated method stub
		
	}


}
