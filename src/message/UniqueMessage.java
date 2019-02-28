package message;

import java.util.UUID;

import src.se.his.drts.message.MessagePayload;

public class UniqueMessage extends MessagePayload {

	protected UniqueMessage(UUID uuid) {
		super(uuid);
	}
	protected UniqueMessage(MessagePayload message) {
		super(message);
	}
	
}
