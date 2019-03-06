package message.bullymessage;

import java.util.UUID;

import org.jgroups.Address;

import message.MessagePayload;

public class ElectionMessasge extends BullyMessage {
	private static UUID messageUUID = UUID.fromString("11dd613e-3f65-11e9-b210-d663bd873d93"); 
	private String m_id; 
	private Address m_address;
	
	
	//Used by jackson to create prototype objects.
	protected ElectionMessasge() {
		super(ElectionMessasge.messageUUID);
	}
	protected ElectionMessasge(UUID uuid) {
		super(uuid);
	}



	@Override
	public String toString() {
		return "ElectionMessasge [ID=" + this.m_id + ", ADDRESS=" + this.m_address + ", toString()=" + super.toString() + "]";
	}
	/* (non-Javadoc)
	 * @see se.his.drts.message.MessagePayload#equals(java.lang.Object)
	 */
//	@Override
//	public boolean equals(Object obj) {
//		return super.equals(obj) && this.name.equals(((ElectionMessasge)obj).name);
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
//		return this.name.compareTo(((ElectionMessasge)arg0).name);
//	}
//	/* (non-Javadoc)
//	 * @see java.lang.Object#hashCode()
//	 */
//	@Override
//	public int hashCode() {
//		return super.hashCode()+name.hashCode();
//	}

	
}
