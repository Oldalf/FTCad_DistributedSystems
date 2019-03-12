package replicaManager;

import java.util.concurrent.LinkedBlockingQueue;

public class ReplicaManagerSendThread implements Runnable {
	private volatile LinkedBlockingQueue<ReplicaManagerOutgoingMessageContainer> messageOutputQueue = new LinkedBlockingQueue<ReplicaManagerOutgoingMessageContainer>();
	
	public ReplicaManagerSendThread(LinkedBlockingQueue<message.Message> messageOutputQueue) {
		
	}

	@Override
	public void run() {

	}

}
