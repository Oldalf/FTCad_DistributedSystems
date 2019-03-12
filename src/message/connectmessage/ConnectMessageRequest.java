package message.connectmessage;

import java.util.UUID;

import State.ReplicaManagerState;
import message.MessagePayload;

public class ConnectMessageRequest extends ConnectMessage {
	private static UUID messageUUID = UUID.fromString("e8cf5202-4027-11e9-b210-d663bd873d93");
	private String m_id;
	
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
		this.m_id = id;
		
	}
	
	@Override
	public void executeForFrontend() {
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
