package replicaManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;

public class ReplicaManager extends ReceiverAdapter {
	JChannel channel;
	String user_name = System.getProperty("user.name", "n/a");
	boolean eventLoopBool = true;

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
		System.out.println("** View: " + new_view);
	}

	public void receive(Message msg) {
		System.out.println(msg.getSrc() + ": " + msg.getObject());
	}

}
