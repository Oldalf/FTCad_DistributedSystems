package replicaManager;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.util.Util;

import DCAD.GObject;
import State.ReplicaManagerState;
import State.rmReplicableState;
import message.bullymessage.ElectionMessage;

public class ReplicaManager extends ReceiverAdapter implements Runnable {
	private ReplicaManagerState state;
	private JChannel channel;
	private String user_name = System.getProperty("user.name", "n/a");
	private boolean eventLoopBool = true;
	private boolean threadLoopBool = true;
	private Thread receiveLogicThread;

	private volatile LinkedBlockingQueue<ReplicaManagerMessageContainer> messageQueue = new LinkedBlockingQueue<ReplicaManagerMessageContainer>();
	private volatile LinkedBlockingQueue<ReplicaManagerMessageContainer> messageOutputQueue = new LinkedBlockingQueue<ReplicaManagerMessageContainer>();

	private Address id;

	public ReplicaManager() {
		state = ReplicaManagerState.getInstance();
		try {
			this.channel = new JChannel().setReceiver(this);
			this.channel.connect("replicaManagerCluster");
			channel.getState(null, 10000);
			id = channel.getAddress();
			// FIXME skapa och starta tråd för sender.

			// eventLoop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void viewAccepted(View new_view) {
		if (state.previousView != null && state.primaryAddress != null) {
			List<Address> leavingMembers = View.leftMembers(state.previousView, new_view);
			if (leavingMembers.contains(state.primaryAddress)) {
				state.primaryMissing = true;
				state.primaryAddress = null;
			}
		}
		state.previousView = new_view;
		System.out.println("** View: " + new_view);
	}

	public void receive(Message msg) {

		System.out.println(msg.getSrc() + ": " + msg.getObject());
		/*
		 * Get uuid, message and message type
		 */
		message.Message incomingMessage = message.Message.deserializeMessage(msg.getObject());
		UUID msgUIID = message.Message.getUUIDFromJSONObject((String) msg.getObject());
		String messageType = message.Message.defineMessageClassWithUUID(msgUIID);
		// add messageContainer to queue.
		messageQueue.add(new ReplicaManagerMessageContainer(incomingMessage, msg.getSrc(), messageType));
	}

	@Override
	public void setState(InputStream input) throws Exception {
		rmReplicableState newRpState;
		newRpState = Util.objectFromStream(new DataInputStream(input));
		synchronized (state.rpState) {
			state.rpState = newRpState;
		}
	}

	@Override
	public void getState(OutputStream output) throws Exception {
		synchronized (state.rpState) {
			Util.objectToStream(state.rpState, new DataOutputStream(output));
		}
	}

	@Override
	public void run() {
		while (threadLoopBool) {
			if (state.primaryAddress == null && state.onGoingElection != true) {
				callElection();
			} else if (state.onGoingElection == true && state.electionTimeout < (System.currentTimeMillis() / 1000)) {
				// I am primary.
				state.primaryAddress = id;
				state.primaryMissing = false;
				/*
				 * FIXME send coordinator broadcast.
				 */
			} else if (messageQueue.size() > 0) {
				try {
					ReplicaManagerMessageContainer rmCont = messageQueue.take();
					message.Message rmMessage = rmCont.getMessage();
					if (state.primaryAddress == id) {
						/*
						 * actions of primary
						 */
						rmMessage.executeForPrimaryReplicaManager(state);
						/*
						 * If message is election either send out victory or drop primary status and
						 * participate in election.
						 */
					} else {
						/*
						 * Actions of normal replica
						 */

						rmMessage.executeForBackupReplicaManager(state);

						/*
						 * If the message is of the type AnswerMesage, stop the election Timer/timeout.
						 */

						/*
						 * If the message is of the type Election Message: do: callElection() send
						 * answerMessage to the sender.
						 */

						/*
						 * If message is of the type Coordinator message, sender is primary
						 */
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}

	}

	private void callElection() {
		state.onGoingElection = true;
		LinkedList<Address> membersWithHigherId = new LinkedList<Address>();
		LinkedList<Address> members = (LinkedList<Address>) state.previousView.getMembers();
		for (int i = 0; i < members.size(); i++) {
			Address member = members.get(i);
			if (!id.equals(member)) {
				if (id.compareTo(member) > 0) {
					membersWithHigherId.add(member);
				}
			}
		}
		/*
		 * Message members.
		 */
		if (membersWithHigherId.size() > 0) {
			for (int i = 0; i < membersWithHigherId.size(); i++) {
				messageOutputQueue
						.add(new ReplicaManagerMessageContainer(new ElectionMessage(id), membersWithHigherId.get(i)));
				/*
				 * FIXME set a timer, either with thread or use the other other run above and
				 * have a timestamp variable.
				 */
			}
			state.electionTimeout = (System.currentTimeMillis() / 1000) + 2;
		} else {
			/*
			 * I am primary, send coordinator message. FIXME ask supervisor if this is a
			 * correct assessment of the algorithm. Seems like once all replicaManagers
			 * realize the primary is gone one of them will realize its the highest and just
			 * elect itself and rarely would the others get time to start the election
			 * process.
			 */
		}
	}

}
