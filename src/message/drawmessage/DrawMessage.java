package message.drawmessage;

import java.util.UUID;

import DCAD.GObject;
import message.Message;
import message.MessagePayload;

public abstract class DrawMessage extends Message{

	
	protected DrawMessage(UUID uuid) {
		super(uuid);
	}
	protected DrawMessage(MessagePayload message) {
		super(message);
	}
}
