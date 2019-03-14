package replicaManager;

import java.util.concurrent.LinkedBlockingQueue;

import org.jgroups.Address;
import org.jgroups.JChannel;

public class ReplicaManagerSendThread implements Runnable {

	private volatile LinkedBlockingQueue<ReplicaManagerMessageContainer> messageOutputQueue = new LinkedBlockingQueue<ReplicaManagerMessageContainer>();
	private JChannel channel;
	private boolean threadLoopBool = true;

	public ReplicaManagerSendThread(LinkedBlockingQueue<ReplicaManagerMessageContainer> messageOutputQueue,
			JChannel channel) {
		this.messageOutputQueue = messageOutputQueue;
		this.channel = channel;
	}

	@Override
	public void run() {
		while (threadLoopBool) {
			if (messageOutputQueue.size() > 0) {
				try {
					ReplicaManagerMessageContainer msgCont = messageOutputQueue.take();
					Address recipientAddress = msgCont.getjGroupAddress();
					message.Message rmMessage = msgCont.getMessage();
					System.out.println("rmMessage uid: " + rmMessage.getUuid());
					System.out.println(rmMessage.getClass().toString());
					channel.send(recipientAddress, rmMessage.serialize());
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
