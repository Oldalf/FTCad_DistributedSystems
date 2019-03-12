package replicaManager;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.util.Util;

import message.bullymessage.ElectionMessage;

public class ReplicaManager extends ReceiverAdapter implements Runnable {
	private JChannel channel;
	private String user_name = System.getProperty("user.name", "n/a");
	private boolean eventLoopBool = true;
	private boolean threadLoopBool = true;
	private boolean primaryMissing = true;
	private Address primaryAddress = null;
	private Address frontendAddress = null;
	private View previousView;
	private Thread receiveLogicThread;

	private volatile LinkedBlockingQueue<message.Message> messageQueue = new LinkedBlockingQueue<message.Message>();
	private volatile LinkedBlockingQueue<ReplicaManagerMessageContainer> messageOutputQueue = new LinkedBlockingQueue<ReplicaManagerMessageContainer>();
	// private final LinkedList<GObject> cadState = new LinkedList<GObject>();
	private final LinkedList<String> cadState = new LinkedList<String>();
	private Address id;

	public ReplicaManager() {
		try {
			this.channel = new JChannel().setReceiver(this);
			this.channel.connect("replicaManagerCluster");
			channel.getState(null, 10000);
			id = channel.getAddress();
			receiveLogicThread = new Thread(new ReplicaManagerReceiveLogicThread(messageQueue));
			receiveLogicThread.start();
			// FIXME skapa och starta tråd för sender.

			// eventLoop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Exhchange this for the actual sending and stuff once replicaManager logioc is
	 * to be implemented
	 */
	private void eventLoop() {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		while (eventLoopBool) {
			try {
				System.out.println("> ");
				String line = "";
				line = in.readLine().toLowerCase();
				if (line.startsWith("quit") || line.startsWith("exit")) {
					eventLoopBool = false;
					break;
				}
				line = "[" + user_name + "]" + line;
				// null = broadcast, line = string som vi ska konvertera
				org.jgroups.Message msg = new Message(null, line);
				channel.send(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}

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
		cadState.add(msg.getObject());

	}

	@SuppressWarnings("unchecked")
	@Override
	public void setState(InputStream input) throws Exception {
		List<String> list;
		list = (List<String>) Util.objectFromStream(new DataInputStream(input));
		synchronized (cadState) {
			cadState.clear();
			cadState.addAll(list);
		}
		System.out.println(list.size() + " messages in chat history");
		for (String str : list) {
			System.out.println(str);
		}
	}

	@Override
	public void getState(OutputStream output) throws Exception {
		synchronized (cadState) {
			Util.objectToStream(cadState, new DataOutputStream(output));
		}
	}

	@Override
	public void run() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		while (threadLoopBool) {
			if (primaryAddress == null) {
				callElection();
			}

			if (messageQueue.size() > 0) {
				try {
					message.Message rmMessage = messageQueue.take();
					if (primaryAddress == id) {
						/*
						 * actions of primary
						 */
						rmMessage.executeForPrimaryReplicaManager();
					} else {
						/*
						 * Actions of normal replica
						 */
						rmMessage.executeForBackupReplicaManager();

						/*
						 * If the message is of the type AnswerMesage, stop the election Timer/timeout.
						 */

						/*
						 * If the message is of the type Election Message: do: callElection() send
						 * answerMEssage to the sender.
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
		LinkedList<Address> membersWithHigherId = new LinkedList<Address>();
		LinkedList<Address> members = (LinkedList<Address>) previousView.getMembers();
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
						.add(new ReplicaManagerMessageContainer(new ElectionMessage(), membersWithHigherId.get(i)));
				/*
				 * FIXME set a timer, either with thread or use the other other run above and
				 * have a timestamp variable.
				 */
			}
		} else {
			/*
			 * I am primary, send coordinator message.
			 * FIXME ask supervisor if this is a correct assessment of the algorithm.
			 * Seems like once all replicaManagers realize the primary is gone one of them will realize its
			 * the highest and just elect itself and rarely would the others get time to start the election process.
			 */
		}
	}

}
