package message.statemessage;

import java.util.LinkedList;
import java.util.UUID;

import DCAD.GObject;
import message.Message;
import message.MessagePayload;
import message.drawmessage.DrawMessage;

public abstract class StateMessage extends Message {
	


	
	protected StateMessage(UUID uuid) {
		super(uuid);
	}
	protected StateMessage(MessagePayload message) {
		super(message);
	}

}
