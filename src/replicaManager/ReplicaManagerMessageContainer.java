package replicaManager;

import org.jgroups.Address;

public class ReplicaManagerMessageContainer {
	private message.Message rmMessage;
	private Address jGroupAddress;
	private String messageType;

	public ReplicaManagerMessageContainer(message.Message rmMessage, Address jGroupAddress, String messageType) {
		super();
		this.rmMessage = rmMessage;
		this.jGroupAddress = jGroupAddress;
		this.messageType = messageType;
	}

	public ReplicaManagerMessageContainer(message.Message rmMessage, Address jGroupAddress) {
		super();
		this.rmMessage = rmMessage;
		this.jGroupAddress = jGroupAddress;
		this.messageType = null;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public message.Message getMessage() {
		return rmMessage;
	}

	public void setMessage(message.Message rmMessage) {
		this.rmMessage = rmMessage;
	}

	public Address getjGroupAddress() {
		return jGroupAddress;
	}

	public void setjGroupAddress(Address jGroupAddress) {
		this.jGroupAddress = jGroupAddress;
	}

}
