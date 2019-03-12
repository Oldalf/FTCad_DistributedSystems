package replicaManager;

import java.util.concurrent.LinkedBlockingQueue;

public class ReplicaManagerSendThread implements Runnable {
	private volatile LinkedBlockingQueue<ReplicaManagerMessageContainer> messageOutputQueue = new LinkedBlockingQueue<ReplicaManagerMessageContainer>();
	
	public ReplicaManagerSendThread(LinkedBlockingQueue<message.Message> messageOutputQueue) {
		
	}

	@Override
	public void run() {
		try {
			ReplicaManagerMessageContainer msgCont = messageOutputQueue.take();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
