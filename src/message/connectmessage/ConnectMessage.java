package message.connectmessage;

import java.util.Optional;
import java.util.UUID;

import message.Message;
import message.MessagePayload;


	


public abstract class ConnectMessage extends Message{
	//private static UUID messageUUID = UUID.fromString("24d84daa-402b-11e9-b210-d663bd873d93");
	
	//Used by jackson to create prototype objects.
	protected ConnectMessage(UUID uuid) {
		super(uuid);
	}
	protected ConnectMessage(MessagePayload message) {
		super(message);
	}


	
	
	
	


}
