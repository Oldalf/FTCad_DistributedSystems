package replicaManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;

public class ReplicaManager extends ReceiverAdapter implements Runnable {
	private JChannel channel;
	private String user_name = System.getProperty("user.name", "n/a");
	private boolean eventLoopBool = true;
	private boolean threadLoopBool = true;
	private boolean primaryMissing = true;
	private Address primaryAddress;
	private View previousView;
	
	/*
	 * replica manager id stuff
	 */
	static final AtomicLong NEXT_ID = new AtomicLong(0);
	private final long id = NEXT_ID.getAndIncrement();

	public ReplicaManager() {
		try {
			this.channel = new JChannel().setReceiver(this);
			this.channel.connect("replicaManagerCluster");
			eventLoop();
			channel.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
		if(previousView!= null && primaryAddress != null) {
			List<Address> leavingMembers = View.leftMembers(previousView, new_view);
			if(leavingMembers.contains(primaryAddress)) {
				primaryMissing = true;
			}
		}
		previousView = new_view;
		System.out.println("** View: " + new_view);
	}

	public void receive(Message msg) {
		System.out.println(msg.getSrc() + ": " + msg.getObject());
	}

	@Override
	public void run() {
		while (threadLoopBool) {
			
		}

	}

}
