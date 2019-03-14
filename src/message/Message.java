package message;

import java.util.Optional;
import java.util.UUID;

import State.FrontendState;
import State.ReplicaManagerState;


public abstract class Message extends MessagePayload {
	private static UUID messageUUID = UUID.fromString("e2cf5202-4027-11e9-b210-d663bd873d93");
	
	protected Message() {
		super(Message.messageUUID);
	}
	
	protected Message(UUID uuid) {
		super(uuid);
		// TODO Auto-generated constructor stub
	}

	protected Message(MessagePayload message) {
		super(message);
	}

	/*
	 * Used to identify the message type
	 * Reutrn: a String with the message class name. 
	 * */
	private static String defineMessageClass(byte[] byteMessage) {
		UUID id = MessagePayload.getUUIDFromJSONObject(byteMessage); //get message id
		MessagePayload message = MessagePayload.getPrototypeMessage(id); //create a temp message
		String messageClass = message.getClass().toString(); //get the class of message
		messageClass = messageClass.replaceAll("class ", ""); //remove everything except the class name.

		return messageClass;
	}
	
	/*	
	 * Serialize the message and return it in byte[] form.
	 *Used soo you dont need to use MessagePayload directly.
	 */
	public static byte[] serializeMessage(Message message) {
		return message.serialize();
	}

	/*
	 * Deserialize a byte[] message and convert it to a message class type.
	 * All other messages classes extends Message so no need to cast to other messages types. 
	 */
	public static Message deserializeMessage(byte[] byteMessage) {
		Optional<MessagePayload> msg = MessagePayload.createMessage(byteMessage);
		return (Message)msg.get();
	}

	/*
	 *Returns the message Class name for the UUID
	 */
	public static String defineMessageClassWithUUID(UUID id) {
		MessagePayload message = MessagePayload.getPrototypeMessage(id); //create a temp message
		String messageClass = message.getClass().toString(); //get the class of message
		System.out.println(messageClass.toString());
		messageClass = messageClass.replaceAll("class message.", ""); //remove everything except the class name.
		return messageClass;
	}
	//Use this execute method in frontend.. 
	public abstract void executeForFrontend(FrontendState state);
	/*
	 *Use this when backup and Primary want's to do the same thing.  
	 */
	public abstract void executeForReplicaManager(ReplicaManagerState state);
	/*
	 *Unique execute for backup 
	 */
	
	public abstract void executeForBackupReplicaManager(ReplicaManagerState state);
	/*
	 *Unique execute for primary 
	 */
	public abstract void executeForPrimaryReplicaManager(ReplicaManagerState state);
}
