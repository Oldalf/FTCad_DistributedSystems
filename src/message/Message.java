package message;

import java.util.Optional;
import java.util.UUID;

public abstract class Message extends MessagePayload {

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
		messageClass = messageClass.replaceAll("class message.", ""); //remove everything except the class name.
		return messageClass;
	}
	
	public static void executeFrontend() {
		
	}
	/*
	 *Use this when backup and Primary want's to do the same thing.  
	 */
	public static void executeForReplicaManager() {
		
	}
	/*
	 *Unique execute for backup 
	 */
	
	public static void executeForBackupReplicaManager() {
		
	}
	/*
	 *Unique execute for primary 
	 */
	public static void executeForPrimaryReplicaManager() {
		
	}
}
