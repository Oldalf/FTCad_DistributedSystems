package replicaManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
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

import State.ReplicaManagerState;
import State.rmReplicableState;
import message.bullymessage.AnswerMessage;
import message.bullymessage.CoordinatorMessage;
import message.bullymessage.ElectionMessage;

public class ReplicaManager extends ReceiverAdapter implements Runnable {
	private ReplicaManagerState state;
	private JChannel channel;
	private boolean threadLoopBool = true;
	private Thread sendRmThread;

	private volatile LinkedBlockingQueue<ReplicaManagerMessageContainer> messageQueue = new LinkedBlockingQueue<ReplicaManagerMessageContainer>();
	private volatile LinkedBlockingQueue<ReplicaManagerMessageContainer> messageOutputQueue = new LinkedBlockingQueue<ReplicaManagerMessageContainer>();

	private Address id;

	public ReplicaManager() {
		state = ReplicaManagerState.getInstance();
		try {
			this.channel = new JChannel().setReceiver(this);
			this.channel.connect("replicaManagerCluster2");
			this.channel.setDiscardOwnMessages(true);
			channel.getState(null, 10000);
			state.stateAcquired = true;

			id = channel.getAddress();
			/*
			 * sendRmThread = new Thread(new ReplicaManagerSendThread(messageOutputQueue,
			 * channel)); sendRmThread.start();
			 */
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
		System.out.println("receiving message: ");
		Message msg2 = msg;
		System.out.println(msg.getSrc()+" buffer: " + msg.getBuffer());
		System.out.println("object: " + msg2.getBuffer());
		
		/*
		 * Get uuid, message and message type
		 */
		message.Message incomingMessage = message.Message.deserializeMessage(msg.getBuffer());
		System.out.println("efter deserial");
		//UUID msgUIID = message.Message.getUUIDFromJSONObject((String) msg.getObject());
		UUID msgUIID = incomingMessage.getUuid();
		System.out.println("efter get uuid");
		String messageType = message.Message.defineMessageClassWithUUID(msgUIID);
		System.out.println("efter definetype");
		// add messageContainer to queue.
		messageQueue.add(new ReplicaManagerMessageContainer(incomingMessage, msg.getSrc(), messageType));
		System.out.println("message received added to message queue!");
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
		while (!state.stateAcquired) {

		}
		System.out.println("State has been acquried, moving on.");

		while (threadLoopBool) {
			// if the primary is null(missing) and there isn't an ongoing election, or if
			// state tells us to call an election
			if ((state.primaryAddress == null && state.onGoingElection != true) || state.callElection) {
				System.out.println("*************");
				System.out.println("case: callElection");
				System.out.println("primary: " + state.primaryAddress);
				System.out.println("ongoingElection: " + state.onGoingElection);
				System.out.println("callElection: " + state.callElection);
				callElection();
			} else if (state.onGoingElection == true
					&& (state.electionTimeout != null && state.electionTimeout < (System.currentTimeMillis() / 1000))) {
				// There is an election but a timeout happened, we should be primary
				// I am primary.
				System.out.println("*************");
				System.out.println("case: timeout");
				System.out.println("ongoingElection: " + state.onGoingElection);
				System.out.println("electionTimeout: " + state.electionTimeout);

				state.electionTimeout = null;
				state.primaryAddress = id;
				state.primaryMissing = false;
				
				messageOutputQueue.add(new ReplicaManagerMessageContainer(new CoordinatorMessage(id), null));
			}

			if (messageQueue.size() > 0) {
				System.out.println("**************");
				System.out.println("case messageQueu");
				System.out.println("primary address:" + state.primaryAddress);
				System.out.println("my id: " + id);
				try {
					ReplicaManagerMessageContainer rmCont = messageQueue.take();
					message.Message rmMessage = rmCont.getMessage();
					if (state.primaryAddress == id) {
						/*
						 * actions of primary
						 */
						rmMessage.executeForPrimaryReplicaManager(state);
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
						 * answerMessage to the sender. TODO fix this with logic above to callElection
						 * using state.!!
						 */
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
			if (messageOutputQueue.size() > 0) {
				try {
					ReplicaManagerMessageContainer msgCont = messageOutputQueue.take();
					Address recipientAddress = msgCont.getjGroupAddress();
					message.Message rmMessage = msgCont.getMessage();
			
					System.out.println("Message uid: " + rmMessage.getUuid());
					System.out.println(rmMessage.getClass().toString());
					System.out.println("address: " + recipientAddress);

					Message msg = new Message(recipientAddress, rmMessage.serialize());
					channel.send(msg);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (state.answerMessageReplyList.size() > 0) {
				System.out.println("*********");
				System.out.println("case anser reply list");
				System.out.println("list size: " + state.answerMessageReplyList.size());
				try {
					Address answerReplyAddress = state.answerMessageReplyList.take();
					messageOutputQueue
							.add(new ReplicaManagerMessageContainer(new AnswerMessage(id), answerReplyAddress));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private void callElection() {
		state.onGoingElection = true;
		// We are currently calling an election, don't need to keep calling.
		state.callElection = false;
		LinkedList<Address> membersWithHigherId = new LinkedList<Address>();
		LinkedList<Address> members = new LinkedList<>();
		members.addAll(state.previousView.getMembers());
		System.out.println("my id is: " + id);
		for (int i = 0; i < members.size(); i++) {
			Address member = members.get(i);
			// Don't add myself or the frontend.
			System.out.println("The member is: " + member);
			if (!id.equals(member) || member.equals(state.frontendAddress)) {
				if (id.compareTo(member) > 0) {
					membersWithHigherId.add(member);
				}
			}
		}
		/*
		 * Message members.
		 */
		System.out.println(membersWithHigherId.size());
		if (membersWithHigherId.size() > 0) {
			for (int i = 0; i < membersWithHigherId.size(); i++) {
				messageOutputQueue
						.add(new ReplicaManagerMessageContainer(new ElectionMessage(id), membersWithHigherId.get(i)));
			}
			state.electionTimeout = (System.currentTimeMillis() / 1000) + 2;
		} else {
			// No other replicaManger has a higher id, announce myself as the electionWinner
			state.primaryAddress = id;
			state.primaryMissing = false;

			messageOutputQueue.add(new ReplicaManagerMessageContainer(new CoordinatorMessage(id), null));
			System.out.println(id + "(me) is the primary");
		}
	}

}
