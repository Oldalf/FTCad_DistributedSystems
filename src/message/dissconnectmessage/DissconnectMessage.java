package message.dissconnectmessage;

import java.util.UUID;

import DCAD.GObject;
import message.Message;
import message.MessagePayload;
import message.connectmessage.ConnectMessage;

public abstract class  DissconnectMessage extends Message{
	
	protected DissconnectMessage(UUID uuid) {
		super(uuid);
	}
	protected DissconnectMessage(MessagePayload message) {
		super(message);
	}

}
