package message.connectmessage;

import java.util.Optional;
import java.util.UUID;

import message.Message;
import message.MessagePayload;


	


public class ConnectMessage extends Message{
	private static UUID messageUUID = UUID.fromString("fe28ead0-3b38-11e9-b210-d663bd873d93");
	private UUID m_id;
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
		this.m_id = id;
		
		if(status.equals(MessageStatus.REPLY)) {
			makeReplyMessage();
		}
		else {
			makeRequestMessage();
		}
	}

	public final UUID getUUID() {
		return this.m_id;
	}
	public MessageStatus getMessageStatus() {
		return this.messageStatus;
	}
	
	@Override
	public String toString() {
		return "ConnectMessage [ClientID=" + this.m_id + ", toString()=" + super.toString() + "]";
	}
	/* (non-Javadoc)
	 * @see se.his.drts.message.MessagePayload#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj) && this.m_id.equals(((ConnectMessage)obj).m_id);
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
		return this.m_id.compareTo(((ConnectMessage)arg0).m_id);
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode()+m_id.hashCode();
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
