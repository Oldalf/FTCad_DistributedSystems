package frontend;

import java.util.List;

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
	private boolean primaryMissing = true;
	private Address id;
	private Address primaryAddress = null;
	private View previousView;
	private FrontendState frotendstate;

	public RmCommunication() {
		try {
			this.frotendstate.getInstance();
			this.channel = new JChannel().setReceiver(this);
			this.channel.connect("replicaManagerCluster");
			id = channel.getAddress();
			sendBroadcastMessage(provideFrontendId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void viewAccepted(View new_view) {
		if (previousView != null && primaryAddress != null) {
			List<Address> leavingMembers = View.leftMembers(previousView, new_view);
			if (leavingMembers.contains(primaryAddress)) {
				primaryMissing = true;
				primaryAddress = null;
			}
		}
		if(previousView != null) {
			List<Address> list = View.newMembers(previousView, new_view);
			if(list.size()>0) {
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
		if(checkForUnwantedMessages(message)) {
			message.executeForFrontend(frotendstate);
		}
		//Vad de är för meddelande
		//Election ska den aldrig svara på
		//answer ska aldrig ske
		//call election
		//
		
		
		System.out.println(msg.getSrc() + ": " + msg.getObject());
		//cadState.add(msg.getObject());

	}

	public void run() {
		while (threadBool) {
			
			
		}
	}
	private message.Message getMessage(Message msg){
		message.Message newMessage = message.Message.deserializeMessage(msg.getObject());
		return newMessage;
	}
	private boolean checkForUnwantedMessages(message.Message msg) {
		if((msg instanceof BullyMessage) && !(msg instanceof CoordinatorMessage)) {
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
			channel.send(null,b);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}