package replicaManager;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.util.Util;

import DCAD.GObject;

public class ReplicaManager extends ReceiverAdapter implements Runnable {
	private JChannel channel;
	private String user_name = System.getProperty("user.name", "n/a");
	private boolean eventLoopBool = true;
	private boolean threadLoopBool = true;
	private boolean primaryMissing = true;
	private Address primaryAddress;
	private View previousView;

	// private final LinkedList<GObject> cadState = new LinkedList<GObject>();
	private final LinkedList<String> cadState = new LinkedList<String>();

	/*
	 * replica manager id stuff FIXME address + port + process id instead of this
	 * solution, this only works if all replica managers are run on the same
	 * machine.
	 */
	static final AtomicLong NEXT_ID = new AtomicLong(0);
	private final long id = NEXT_ID.getAndIncrement();

	public ReplicaManager() {
		try {
			this.channel = new JChannel().setReceiver(this);
			this.channel.connect("replicaManagerCluster");
			channel.getState(null, 10000);
			eventLoop();
			channel.close();
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
				// null = broadcast line = string som vi ska konvertera
				Message msg = new Message(null, line);
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
		while (threadLoopBool) {

		}

	}

}