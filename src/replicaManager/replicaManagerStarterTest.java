package replicaManager;

public class replicaManagerStarterTest {

	public static void main(String[] args) {
		Thread t = new Thread(new ReplicaManager());
		t.start();

	}

}
