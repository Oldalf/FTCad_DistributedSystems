package replicaManager;

public class replicaManagerStarterTest {

	public static void main(String[] args) {
		String processName = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
		Long pid = Long.parseLong(processName.split("@")[0]);
		System.out.println(pid);
		Thread t = new Thread(new ReplicaManager());
		t.start();

	}

}
