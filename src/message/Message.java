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
	
	protected abstract void makeReplyMessage();
	protected abstract void makeRequestMessage();
	
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
		Message msg2 = (Message) MessagePayload.getPrototypeMessage(MessagePayload.getUUIDFromJSONObject(byteMessage));
		Optional<MessagePayload> msg =  msg2.deserialize(byteMessage);
		Message msg1 = (Message)msg.get();
		return msg1;
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
	
	
}
