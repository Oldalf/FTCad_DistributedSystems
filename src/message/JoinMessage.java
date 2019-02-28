package message;

import java.util.UUID;

import src.se.his.drts.message.MessagePayload;


	


public class JoinMessage extends MessagePayload{
	private static UUID uuid = UUID.fromString("fe28ead0-3b38-11e9-b210-d663bd873d93");
	private String name;
	//Used by jackson to create prototype objects.
	protected JoinMessage() {
		super(JoinMessage.uuid);
	}
	protected JoinMessage(UUID uuid) {
		super(JoinMessage.uuid);
	}
	protected JoinMessage(MessagePayload message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	public JoinMessage(String name) {
		super(JoinMessage.uuid);
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "TestUniqueMessage [name=" + name + ", toString()=" + super.toString() + "]";
	}
	/* (non-Javadoc)
	 * @see se.his.drts.message.MessagePayload#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj) && this.name.equals(((JoinMessage)obj).name);
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
		return this.name.compareTo(((JoinMessage)arg0).name);
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode()+name.hashCode();
	}
	


	
	


}
