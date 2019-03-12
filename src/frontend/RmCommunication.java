package frontend;

import java.util.List;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;

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
			channel.getState(null, 10000);
			id = channel.getAddress();
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
		previousView = new_view;
		System.out.println("** View: " + new_view);
	}

	/**
	 * FIXME add messages to a processing list.
	 */
	public void receive(Message msg) {

		System.out.println(msg.getSrc() + ": " + msg.getObject());
		//cadState.add(msg.getObject());

	}

	public void run() {
		while (threadBool) {
			listenForRequest();
		}
	}

	private void listenForRequest() {

	}
}