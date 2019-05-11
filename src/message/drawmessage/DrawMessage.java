package message.drawmessage;

import java.util.UUID;

import message.Message;
import message.MessagePayload;

public abstract class DrawMessage extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static UUID messageUUID = UUID.fromString("8f2fd462-4737-11e9-b210-d663bd873d93");

	protected DrawMessage(UUID uuid) {
		super(uuid);
	}

	protected DrawMessage(MessagePayload message) {
		super(message);
	}
}
