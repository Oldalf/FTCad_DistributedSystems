package message;

import java.util.LinkedList;
import java.util.UUID;

import DCAD.GObject;

public class StateMessage extends Message {
	private static UUID messageUUID = UUID.fromString("488eb60e-3dbe-11e9-b210-d663bd873d93");
	private MessageStatus messageStatus; 
	private LinkedList<GObject> state;
	protected StateMessage() {
		super(StateMessage.messageUUID);
	}
	protected StateMessage(UUID uuid) {
		super(StateMessage.messageUUID);
	}
	protected StateMessage(MessagePayload message) {
		super(message);
	}
	public StateMessage(LinkedList<GObject> state, MessageStatus status) {
		super(StateMessage.messageUUID);
		this.state = state;
		
		if(status.equals(MessageStatus.REPLY)) {
			makeReplyMessage();
		}
		else {
			makeRequestMessage();
		}
	}
	@Override
	public String toString() {
		return "StateMessage [State=" + this.state + ", toString()=" + super.toString() + "]";
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
		return this.messageStatus;
	}

}
