package replicaManager;

import org.jgroups.Address;

import message.Message;

public class ReplicaManagerMessageContainer {
	private message.Message rmMessage;
	private Address replicaAddress;
	private String messageType;

	public ReplicaManagerMessageContainer(Message rmMessage, Address replicaAddress, String messageType) {
		super();
		this.rmMessage = rmMessage;
		this.replicaAddress = replicaAddress;
		this.messageType = messageType;
	}
	
	public ReplicaManagerMessageContainer(Message rmMessage, Address replicaAddress) {
		super();
		this.rmMessage = rmMessage;
		this.replicaAddress = replicaAddress;
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

	public void setMessage(message.Message outMessage) {
		this.rmMessage = outMessage;
	}

	public Address getReplicaAddress() {
		return replicaAddress;
	}

	public void setReplicaAddress(Address replicaAddress) {
		this.replicaAddress = replicaAddress;
	}

}
