package replicaManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.util.Util;

import State.ReplicaManagerState;
import State.rmReplicableState;
import message.MessagePayload;
import message.bullymessage.AnswerMessage;
import message.bullymessage.CoordinatorMessage;
import message.bullymessage.ElectionMessage;
import message.drawmessage.DrawMessageReply;
import message.removedrawmessage.RemoveDrawMessageReply;
import replicaManager.RequestContainer.RequestStage;
import replicaManager.RequestContainer.requestType;

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
			this.channel = new JChannel();
			this.channel.setReceiver(this);
			this.channel.connect("replicaManagerCluster2");
			this.channel.setDiscardOwnMessages(true);
			channel.getState(null, 10000);
			state.stateAcquired = true;

			id = channel.getAddress();

			/*
			 * FIXME ta bort det h�r senare, har kvar f�r o testa addresConvertern bara
			 * org.jgroups.util.UUID testid = (org.jgroups.util.UUID) id; AddressConverter
			 * AC = new AddressConverter(testid.getMostSignificantBits(),testid.
			 * getLeastSignificantBits()); org.jgroups.util.UUID testid2 =
			 * AC.getAndPossiblyCreateJGroupsUUID(); System.out.println("testid1: "+testid);
			 * System.out.println("testid1 long: "+testid.toStringLong());
			 * 
			 * System.out.println("testid2: "+testid2);
			 * System.out.println("testid2 long: "+testid2.toStringLong());
			 * 
			 * System.out.println("testid1: compared to testid2: "+testid.compareTo(testid2)
			 * ); System.out.println("testid1 �r testid2: "+testid.equals(testid2)); /*
			 */

			sendRmThread = new Thread(new ReplicaManagerSendThread(messageOutputQueue, channel));
			sendRmThread.start();

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
		System.out.println(msg.getSrc() + " buffer: " + msg.getBuffer().toString());

		/*
		 * Get uuid, message and message type
		 */

		message.Message incomingMessage = message.Message.deserializeMessage(msg.getBuffer());
		UUID msgUIID = incomingMessage.getUuid();
		String messageType = message.Message.defineMessageClassWithUUID(msgUIID);

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

				org.jgroups.util.UUID JGroupUUID = (org.jgroups.util.UUID) id;
				messageOutputQueue.add(new ReplicaManagerMessageContainer(new CoordinatorMessage(new AddressConverter(
						JGroupUUID.getMostSignificantBits(), JGroupUUID.getLeastSignificantBits())), null));
			}

			if (messageQueue.size() > 0) {
				System.out.println("**************");
				System.out.println("case messageQueu");
				System.out.println("primary address:" + state.primaryAddress);
				System.out.println("my id: " + id);

				// using poll just in case the state is changed through a message we don't want
				// to get
				// stuck waiting.
				ReplicaManagerMessageContainer rmCont = messageQueue.poll();
				if (rmCont != null) {

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
					}
				}

			}

			// Make one of these for respones and requests.

			if (state.responseList.size() > 0) {
				System.out.println("*********");
				System.out.println("case response list");

				// using poll just in case the state is changed through a message and we don't
				// want to get stuck waiting.
				ReplicaManagerMessageContainer msgCont = state.responseList.poll();
				if (msgCont != null) {
					messageOutputQueue.add(msgCont);
				}
			}

			if (state.rpState.ReadyToSendRequests.size() > 0) {
				RequestContainer requestReply = state.rpState.ReadyToSendRequests.poll();
				if (requestReply != null) {
					requestReply.setStage(RequestStage.SentToFrontEnd);
					if (requestReply.getType() == requestType.Draw) {
						// FIXME byt konstruktor n�r den nya finns
						DrawMessageReply reply = new DrawMessageReply();
						messageOutputQueue.add(new ReplicaManagerMessageContainer(reply, state.frontendAddress));
					} else {
						// its a remove type.
						// FIXME byt konstruktor n�r den nya finns
						RemoveDrawMessageReply reply = new RemoveDrawMessageReply();
						messageOutputQueue.add(new ReplicaManagerMessageContainer(reply, state.frontendAddress));
					}
				}
			}

			if (state.answerMessageReplyList.size() > 0) {
				System.out.println("*********");
				System.out.println("case answer reply list");

				// using poll just in case the state is changed through a message we don't want
				// to get
				// stuck waiting.
				Address answerReplyAddress = state.answerMessageReplyList.poll();
				if (answerReplyAddress != null) {

					org.jgroups.util.UUID JGroupUUID = (org.jgroups.util.UUID) id;
					messageOutputQueue.add(new ReplicaManagerMessageContainer(
							new AnswerMessage(new AddressConverter(JGroupUUID.getMostSignificantBits(),
									JGroupUUID.getLeastSignificantBits())),
							answerReplyAddress));
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
		for (int i = 0; i < members.size(); i++) {
			Address member = members.get(i);
			// Don't add myself or the frontend.
			if (!id.equals(member) || member.equals(state.frontendAddress)) {
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
				org.jgroups.util.UUID JGroupUUID = (org.jgroups.util.UUID) id;
				messageOutputQueue.add(new ReplicaManagerMessageContainer(
						new ElectionMessage(new AddressConverter(JGroupUUID.getMostSignificantBits(),
								JGroupUUID.getLeastSignificantBits())),
						membersWithHigherId.get(i)));
			}
			state.electionTimeout = (System.currentTimeMillis() / 1000) + 2;
		} else {
			// No other replicaManger has a higher id, announce myself as the electionWinner
			state.primaryAddress = id;
			state.primaryMissing = false;
			org.jgroups.util.UUID JGroupUUID = (org.jgroups.util.UUID) id;
			messageOutputQueue.add(new ReplicaManagerMessageContainer(new CoordinatorMessage(
					new AddressConverter(JGroupUUID.getMostSignificantBits(), JGroupUUID.getLeastSignificantBits())),
					null));
			System.out.println(id + "(me) is the primary");
		}
	}

}
