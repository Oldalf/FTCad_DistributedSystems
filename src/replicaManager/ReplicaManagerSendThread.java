package replicaManager;

import java.util.concurrent.LinkedBlockingQueue;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;

public class ReplicaManagerSendThread implements Runnable {

	private volatile LinkedBlockingQueue<ReplicaManagerMessageContainer> messageOutputQueue = new LinkedBlockingQueue<ReplicaManagerMessageContainer>();
	private JChannel channel;
	private boolean isAlive = true;

	public ReplicaManagerSendThread(LinkedBlockingQueue<ReplicaManagerMessageContainer> messageOutputQueue,
			JChannel channel) {
		this.messageOutputQueue = messageOutputQueue;
		this.channel = channel;
	}

	@Override
	public void run() {
		while (isAlive) {
			if (messageOutputQueue.size() > 0) {
				try {
					ReplicaManagerMessageContainer msgCont = messageOutputQueue.take();

					Address recipientAddress = msgCont.getjGroupAddress();
					message.Message rmMessage = msgCont.getMessage();

					System.out.println("Message uid: " + rmMessage.getUuid());
					System.out.println(rmMessage.getClass().toString());
					System.out.println("address: " + recipientAddress);
					String test2 = new String(rmMessage.serialize());
					System.out.println("output serialised and made to string: " + test2);
					Message msg = new Message(recipientAddress, rmMessage.serialize());
					channel.send(msg);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void kill() {
		// leave while loop in run and die.
		isAlive = false;
	}

}
