package message.removedrawmessage;

import java.util.UUID;

import DCAD.GObject;
import State.FrontendState;
import State.ReplicaManagerState;
import message.MessagePayload;

public class RemoveDrawMessageRequest extends RemoveDrawMessage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static UUID messageUUID = UUID.fromString("dfaa8aec-489d-11e9-8646-d663bd873d93");

	private GObject object;
	
	public RemoveDrawMessageRequest()
	{
		super(RemoveDrawMessageRequest.messageUUID);
	}
	
	public RemoveDrawMessageRequest(GObject object)
	{
		super(RemoveDrawMessageRequest.messageUUID);
		this.object = object;
	}
	
	protected RemoveDrawMessageRequest(UUID uuid) 
	{
		super(uuid);
	}
	
	protected RemoveDrawMessageRequest(MessagePayload message) 
	{
		super(message);
	}
	
	public void setObject(GObject object)
	{
		this.object = object;
	}
	
	public GObject getObject()
	{
		return this.object;
	}
	@Override
	public void executeForFrontend(FrontendState state) {

	}

	@Override
	public void executeForReplicaManager(ReplicaManagerState state) {

	}

	@Override
	public void executeForBackupReplicaManager(ReplicaManagerState state) {

	}

	@Override
	public void executeForPrimaryReplicaManager(ReplicaManagerState state) {

	}
}