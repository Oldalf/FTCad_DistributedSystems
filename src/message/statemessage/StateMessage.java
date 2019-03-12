package message.statemessage;

import java.util.LinkedList;
import java.util.UUID;

import DCAD.GObject;
import message.Message;
import message.MessagePayload;
import message.drawmessage.DrawMessage;

public abstract class StateMessage extends Message {
	private static UUID messageUUID = UUID.fromString("1950556a-40f6-11e9-b210-d663bd873d93");

	public StateMessage() {
		super(StateMessage.messageUUID);
	}
	
	protected StateMessage(UUID uuid) {
		super(uuid);
	}
	protected StateMessage(MessagePayload message) {
		super(message);
	}

}
