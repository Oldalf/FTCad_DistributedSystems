package frontend;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;

import State.FrontendState;
import message.bullymessage.BullyMessage;
import message.bullymessage.CoordinatorMessage;
import message.frontendmessage.FrontendMessage;

// Classen som ska kommunicera mellan Server och Replica Managerserna!
class RmCommunication extends ReceiverAdapter implements Runnable {
	private JChannel channel;
	private boolean threadBool = true;
	//private boolean primaryMissing = true;
	private Address id;
	//private Address primaryAddress = null;
	private View previousView;
	private FrontendState frontendstate;
	private LinkedBlockingQueue<message.Message> receiveQueue;
	private LinkedBlockingQueue<message.Message> sendQueue = new LinkedBlockingQueue<message.Message>();
	private Thread sendToMessageHandler;
	private MessageHandlerThread MessageHandlerThreadInstance;
	public RmCommunication(LinkedBlockingQueue<message.Message> queueBetweenClients) {
		try {
			this.frontendstate = FrontendState.getInstance();
			this.channel = new JChannel().setReceiver(this);
			this.channel.connect("replicaManagerCluster");
			this.channel.setDiscardOwnMessages(true);
			this.id = channel.getAddress();
			sendBroadcastMessage(provideFrontendId());
			this.receiveQueue = queueBetweenClients;
			this.MessageHandlerThreadInstance = new MessageHandlerThread(sendQueue);
			this.sendToMessageHandler = new Thread(MessageHandlerThreadInstance);
			this.sendToMessageHandler.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void viewAccepted(View new_view) {
		if (previousView != null && frontendstate.primaryAddress != null) {
			List<Address> leavingMembers = View.leftMembers(previousView, new_view);
			if (leavingMembers.contains(frontendstate.primaryAddress)) {
				frontendstate.primaryMissing = true;
				frontendstate.primaryAddress = null;
			}
		}
		if (previousView != null) {
			List<Address> list = View.newMembers(previousView, new_view);
			if (list.size() > 0) {
				sendBroadcastMessage(provideFrontendId());
			}
		}
		previousView = new_view;
		System.out.println("** View: " + new_view);
	}

	/**
	 * FIXME add messages to a processing list.
	 */
	public void receive(Message msg) {
		message.Message message = getMessage(msg);
		if (checkForUnwantedMessages(message)) {
			if (isMessageToClient(message)) {
				sendQueue.add(message);
			} else {
				message.executeForFrontend(frontendstate);
			}

		}
	}

	public void run() {
		while (threadBool) {
			try {
				message.Message msg = receiveQueue.take();
				if(frontendstate.primaryAddress != null && frontendstate.primaryMissing == false ) {
					sendToPrimary(message.Message.serializeMessage(msg));			
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private boolean isMessageToClient(message.Message msg) {
		if (msg instanceof CoordinatorMessage) {
			return false;
		}
		return true;

	}

	private message.Message getMessage(Message msg) {
		message.Message newMessage = message.Message.deserializeMessage(msg.getObject());
		return newMessage;
	}

	private boolean checkForUnwantedMessages(message.Message msg) {
		if ((msg instanceof BullyMessage) && !(msg instanceof CoordinatorMessage)) {
			return false;
		}
		return true;
	}

	private byte[] provideFrontendId() {
		FrontendMessage fm = new FrontendMessage(id);
		byte[] b = message.Message.serializeMessage(fm);
		return b;

	}

	private void sendBroadcastMessage(byte[] b) {
		try {
			channel.send(null, b);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void sendToPrimary(byte[] b) {
		try {
			channel.send(frontendstate.primaryAddress, b);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void endProcess() {
		this.threadBool = false;
		this.MessageHandlerThreadInstance.endProcess();
		this.channel.disconnect();
		this.channel.close();
		
		
	}
}