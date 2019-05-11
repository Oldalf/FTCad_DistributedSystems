package message.removedrawmessage;

import java.util.UUID;

import DCAD.GObject;
import State.FrontendState;
import State.ReplicaManagerState;
import message.MessagePayload;
import message.Reply;

public class RemoveDrawMessageReply extends RemoveDrawMessage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static UUID messageUUID = UUID.fromString("1934207e-489f-11e9-8646-d663bd873d93");
	
	private GObject object;
	private Reply reply;

	protected RemoveDrawMessageReply(MessagePayload message) 
	{
		super(message);
	}
	
	protected RemoveDrawMessageReply(UUID uuid) 
	{
		super(uuid);
	}
	
	public RemoveDrawMessageReply()
	{
		super(RemoveDrawMessageReply.messageUUID);
	}
	
	public RemoveDrawMessageReply(GObject object, Reply reply)
	{
		super(RemoveDrawMessageReply.messageUUID);
		this.reply = reply;
		this.object = object;
	}
	
	public void setObject(GObject object)
	{
		this.object = object;
	}
	
	public GObject getObject()
	{
		return this.object;
	}
	
	public void setReply(Reply reply)
	{
		this.reply = reply;
	}
	
	public Reply getReply()
	{
		return this.reply;
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
