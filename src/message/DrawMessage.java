package message;

import java.util.UUID;

import DCAD.GObject;

public class DrawMessage extends Message{
	private static UUID messageUUID = UUID.fromString("488eb14a-3dbe-11e9-b210-d663bd873d93");
	private MessageStatus messageStatus;
	private GObject object;
	
	protected DrawMessage() {
		super(DrawMessage.messageUUID);
	}
	
	protected DrawMessage(UUID uuid) {
		super(uuid);
		// TODO Auto-generated constructor stub
	}
	protected DrawMessage(MessagePayload message) {
		super(message);
	}
	public DrawMessage(String id, MessageStatus status, GObject newObject) {
		super(DrawMessage.messageUUID);
		this.object = newObject;
		if(status.equals(MessageStatus.REPLY)) {
			makeReplyMessage();
		}
		else {
			makeRequestMessage();
		}
	}
	@Override
	public String toString() {
		return "DrawMessage [DrawObject=" + this.object + ", toString()=" + super.toString() + "]";
	}
	/* (non-Javadoc)
	 * @see se.his.drts.message.MessagePayload#equals(java.lang.Object)
	 */
//	@Override
//	public boolean equals(Object obj) {
//		return super.equals(obj) && this.object.equals(((ConnectMessage)obj).name);
//	}
//	/* (non-Javadoc)
//	 * @see se.his.drts.message.MessagePayload#compareTo(se.his.drts.message.MessagePayload)
//	 */
//	@Override
//	public int compareTo(MessagePayload arg0) {
//		final int n = super.compareTo(arg0);
//		if (n!=0) {
//			return n;
//		}
//		return this.object.compareTo(((DrawMessage)arg0).object);
//	}
//	/* (non-Javadoc)
//	 * @see java.lang.Object#hashCode()
//	 */
//	@Override
//	public int hashCode() {
//		return super.hashCode()+name.hashCode();
//	}

	@Override
	protected void makeReplyMessage() {
		this.messageStatus = MessageStatus.REPLY;
		
	}

	@Override
	protected void makeRequestMessage() {
		this.messageStatus = MessageStatus.REQUEST;
		
	}

}
