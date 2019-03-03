package message;

import java.util.UUID;

import DCAD.GObject;

public class DissconnectMessage extends Message{
	private static UUID messageUUID = UUID.fromString("488eb4a6-3dbe-11e9-b210-d663bd873d93");
	private MessageStatus messageStatus;
	private UUID id;
	
	protected DissconnectMessage() {
		super(DissconnectMessage.messageUUID);
		// TODO Auto-generated constructor stub
	}
	
	protected DissconnectMessage(UUID uuid) {
		super(uuid);
		// TODO Auto-generated constructor stub
	}
	protected DissconnectMessage(MessagePayload message) {
		super(message);
	}
	public DissconnectMessage(MessageStatus status, UUID id) {
		super(DissconnectMessage.messageUUID);
		this.id = id;
		if(status.equals(MessageStatus.REPLY)) {
			makeReplyMessage();
		}
		else {
			makeRequestMessage();
		}
	}
	
	@Override
	public String toString() {
		return "DissconnectMessage [ID=" + this.id + ", toString()=" + super.toString() + "]";
	}
	@Override
	protected void makeReplyMessage() {
		this.messageStatus = MessageStatus.REPLY;
		
	}

	@Override
	protected void makeRequestMessage() {
		this.messageStatus = MessageStatus.REQUEST;
		
	}

	@Override
	protected MessageStatus getMessageStatus() {
		// TODO Auto-generated method stub
		return this.messageStatus;
	}

}
