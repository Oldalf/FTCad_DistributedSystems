package replicaManager;

import org.jgroups.Address;

import message.Message;

public class ReplicaManagerMessageContainer {
	private message.Message rmMessage;
	private Address replicaAddress;

	public ReplicaManagerMessageContainer(Message rmMessage, Address replicaAddress) {
		super();
		this.rmMessage = rmMessage;
		this.replicaAddress = replicaAddress;
	}

	public message.Message getOutMessage() {
		return rmMessage;
	}

	public void setOutMessage(message.Message outMessage) {
		this.rmMessage = outMessage;
	}

	public Address getReplicaAddress() {
		return replicaAddress;
	}

	public void setReplicaAddress(Address replicaAddress) {
		this.replicaAddress = replicaAddress;
	}

}
