package message.dissconnectmessage;

import java.util.UUID;

import message.Message;
import message.MessagePayload;

public abstract class  DissconnectMessage extends Message{
	
	protected DissconnectMessage(UUID uuid) {
		super(uuid);
	}
	protected DissconnectMessage(MessagePayload message) {
		super(message);
	}

}
