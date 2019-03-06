package replicaManager;

import java.util.concurrent.LinkedBlockingQueue;

public class ReplicaManagerReceiveLogicThread implements Runnable {
	private boolean threadLoopBool = true;
	private volatile LinkedBlockingQueue<message.Message> messageQueue = new LinkedBlockingQueue<message.Message>();

	public ReplicaManagerReceiveLogicThread(LinkedBlockingQueue<message.Message> messageQueue) {
		this.messageQueue = messageQueue;
	}

	@Override
	public void run() {
		while (threadLoopBool) {

			if (messageQueue.size() > 0) {
				try {
					message.Message rmMessage = messageQueue.take();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
