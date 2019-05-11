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
import message.drawmessage.DrawMessage;
import message.drawmessage.DrawMessageRequest;
import message.frontendmessage.FrontendMessage;
import message.removedrawmessage.RemoveDrawMessage;
import message.removedrawmessage.RemoveDrawMessageRequest;
import message.statemessage.StateMessage;
import message.statemessage.StateMessageRequest;

// Classen som ska kommunicera mellan Server och Replica Managerserna!
class RmCommunication extends ReceiverAdapter implements Runnable {
	private JChannel channel;
	private boolean threadBool = true;
	private boolean primaryMissing = true;
	private Address id;
	private Address primaryAddress = null;
	private View previousView;
	private FrontendState frotendstate;
	private LinkedBlockingQueue<message.Message> receiveQueue;
	private LinkedBlockingQueue<message.Message> sendQueue = new LinkedBlockingQueue<message.Message>();

	public RmCommunication(LinkedBlockingQueue<message.Message> queueBetweenClients) {
		try {
			this.frotendstate = FrontendState.getInstance();
			this.channel = new JChannel().setReceiver(this);
			this.channel.connect("replicaManagerCluster");
			this.channel.setDiscardOwnMessages(true);
			id = channel.getAddress();
			sendBroadcastMessage(provideFrontendId());
			this.receiveQueue = queueBetweenClients;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Thread messageHanderThread = new Thread(new MessageHandlerThread(sendQueue));
		messageHanderThread.start();
	}

	public void viewAccepted(View new_view) {
		if (previousView != null && primaryAddress != null) {
			List<Address> leavingMembers = View.leftMembers(previousView, new_view);
			if (leavingMembers.contains(primaryAddress)) {
				frotendstate.primaryMissing = true;
				frotendstate.primaryAddress = null;
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
				receiveQueue.add(message);
			} else {
				message.executeForFrontend(frotendstate); //Message is a cordinatormessage. 
			}

		}
	}

	public void run() {
		while (threadBool) {
			try {
				message.Message msg = receiveQueue.take();
				
				if(msg instanceof DrawMessage || msg instanceof RemoveDrawMessage || msg instanceof StateMessage) {
					if(msg instanceof DrawMessageRequest || msg instanceof RemoveDrawMessageRequest || msg instanceof StateMessageRequest ) {
						sendToPrimary(message.Message.serializeMessage(msg));
					}else {
						sendQueue.add(msg);
					}
				} else {
					msg.executeForFrontend(frotendstate); //Message most be a frontendMessage. 
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
			channel.send(primaryAddress, b);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}