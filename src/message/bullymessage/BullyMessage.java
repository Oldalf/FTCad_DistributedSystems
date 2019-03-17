package message.bullymessage;

import java.util.UUID;

import message.Message;
import replicaManager.AddressConverter;

public abstract class BullyMessage extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6598173601282459442L;
	protected AddressConverter addressC;
	
	public BullyMessage(UUID uuid) {
		super(uuid);
	}

	public AddressConverter getAddressC() {
		return addressC;
	}

	public void setAddressC(AddressConverter addressC) {
		this.addressC = addressC;
	}
	

}
