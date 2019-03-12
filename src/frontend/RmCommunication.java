package frontend;

import java.util.List;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;

import message.bullymessage.FrontendMessage;

// Classen som ska kommunicera mellan Server och Replica Managerserna!
class RmCommunication extends ReceiverAdapter implements Runnable {
	private JChannel channel;
	private boolean threadBool = true;
	private boolean primaryMissing = true;
	private Address id;
	private Address primaryAddress = null;
	private View previousView;

	public RmCommunication() {
		try {
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