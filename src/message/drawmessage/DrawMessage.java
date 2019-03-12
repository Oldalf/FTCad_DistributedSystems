package message.drawmessage;

import java.util.UUID;

import DCAD.GObject;
import message.Message;
import message.MessagePayload;

public abstract class DrawMessage extends Message{
	private static UUID messageUUID = UUID.fromString("488eb14a-3dbe-11e9-b210-d663bd873d93");

	public DrawMessage() {
		super(DrawMessage.messageUUID);
	}
	
	protected DrawMessage(UUID uuid) {
		super(uuid);
	}
	protected DrawMessage(MessagePayload message) {
		super(message);
	}
}
