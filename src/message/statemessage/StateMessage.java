package message.statemessage;

import java.util.UUID;

import message.Message;
import message.MessagePayload;

public abstract class StateMessage extends Message {
	


	
	protected StateMessage(UUID uuid) {
		super(uuid);
	}
	protected StateMessage(MessagePayload message) {
		super(message);
	}

}
