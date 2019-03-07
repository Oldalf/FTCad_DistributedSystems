package message.dissconnectmessage;

import java.util.UUID;

public class DissconnectMessageReply extends DissconnectMessage{
	private static UUID messageUUID = UUID.fromString("488eb4a6-3dbe-11e9-b210-d663bd873d93");
	
	
	
	
	
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
