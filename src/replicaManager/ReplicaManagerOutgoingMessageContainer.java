package replicaManager;

import org.jgroups.Address;

import message.Message;

public class ReplicaManagerOutgoingMessageContainer {
	private message.Message outMessage;
	private Address replicaAddress;

	public ReplicaManagerOutgoingMessageContainer(Message outMessage, Address replicaAddress) {
		super();
		this.outMessage = outMessage;
		this.replicaAddress = replicaAddress;
	}

	public message.Message getOutMessage() {
		return outMessage;
	}

	public void setOutMessage(message.Message outMessage) {
		this.outMessage = outMessage;
	}

	public Address getReplicaAddress() {
		return replicaAddress;
	}

	public void setReplicaAddress(Address replicaAddress) {
		this.replicaAddress = replicaAddress;
	}

}
