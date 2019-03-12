package message;

import java.util.Optional;
import java.util.UUID;


public class ConnectMessage extends Message{

	private String textMessage = "";

	private static UUID messageUUID = UUID.fromString("fe28ead0-3b38-11e9-b210-d663bd873d93");
	private UUID ID;
	private MessageStatus messageStatus; 
	//Used by jackson to create prototype objects.
	protected ConnectMessage() {
		super(ConnectMessage.messageUUID);
	}
	protected ConnectMessage(UUID uuid) {
		super(ConnectMessage.messageUUID);
	}
	protected ConnectMessage(MessagePayload message) {
		super(message);
	}
	public ConnectMessage(UUID id, MessageStatus status) {
		super(ConnectMessage.messageUUID);
		this.ID = id;

		if(status.equals(MessageStatus.REPLY)) {
			makeReplyMessage();
		}
		else {
			makeRequestMessage();
		}
	}

	public ConnectMessage(String message, MessageStatus status)
	{
		super(ConnectMessage.messageUUID);
		textMessage = message;

		if(status.equals(MessageStatus.REPLY)) 
			makeReplyMessage();

		else 
			makeRequestMessage();
	}

	public String getTextMessage()
	{
		return textMessage;
	}

	public final UUID getName() {
		return this.ID;
	}
	public MessageStatus getMessageStatus() {
		return this.messageStatus;
	}

	@Override
	public String toString() {
		return "ConnectMessage [ClientID=" + this.ID + ", toString()=" + super.toString() + "]";
	}
	/* (non-Javadoc)
	 * @see se.his.drts.message.MessagePayload#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj) && this.ID.equals(((ConnectMessage)obj).ID);
	}
	/* (non-Javadoc)
	 * @see se.his.drts.message.MessagePayload#compareTo(se.his.drts.message.MessagePayload)
	 */
	@Override
	public int compareTo(MessagePayload arg0) {
		final int n = super.compareTo(arg0);
		if (n!=0) {
			return n;
		}
		return this.ID.compareTo(((ConnectMessage)arg0).ID);
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode()+ID.hashCode();
	}
	@Override
	protected void makeReplyMessage() {
		this.messageStatus = MessageStatus.REPLY;

	}
	@Override
	protected void makeRequestMessage() {
		this.messageStatus = MessageStatus.REQUEST;

	}







}
