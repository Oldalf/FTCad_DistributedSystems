package message;

import java.util.Optional;
import java.util.UUID;


	


public class ConnectMessage extends Message{
	private static UUID messageUUID = UUID.fromString("fe28ead0-3b38-11e9-b210-d663bd873d93");
	private String name;
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
	public ConnectMessage(String id, MessageStatus status) {
		super(ConnectMessage.messageUUID);
		this.name = id;
		
		if(status.equals(MessageStatus.REPLY)) {
			makeReplyMessage();
		}
		else {
			makeRequestMessage();
		}
	}

	public final String getName() {
		return this.name;
	}
	public MessageStatus getMessageStatus() {
		return this.messageStatus;
	}
	
	@Override
	public String toString() {
		return "ConnectMessage [ClientID=" + this.name + ", toString()=" + super.toString() + "]";
	}
	/* (non-Javadoc)
	 * @see se.his.drts.message.MessagePayload#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj) && this.name.equals(((ConnectMessage)obj).name);
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
		return this.name.compareTo(((ConnectMessage)arg0).name);
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode()+name.hashCode();
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
