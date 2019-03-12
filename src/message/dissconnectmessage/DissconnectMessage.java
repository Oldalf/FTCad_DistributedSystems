package message.dissconnectmessage;

import java.util.UUID;

import DCAD.GObject;
import message.Message;
import message.MessagePayload;
import message.connectmessage.ConnectMessage;

public abstract class  DissconnectMessage extends Message{
	private static UUID messageUUID = UUID.fromString("488eb4a6-3dbe-11e9-b210-d663bd873d93");
	
	protected DissconnectMessage() {
		super(DissconnectMessage.messageUUID);
	}
	
	protected DissconnectMessage(UUID uuid) {
		super(uuid);
	}
	protected DissconnectMessage(MessagePayload message) {
		super(message);
	}

}
