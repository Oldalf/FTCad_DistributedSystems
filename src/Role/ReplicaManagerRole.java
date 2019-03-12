package Role;

public class ReplicaManagerRole extends AbstractRole {
	private static ReplicaManagerRole single_role = null;
	
	
	protected ReplicaManagerRole() {
		
	}
	
	public static ReplicaManagerRole getInstance() {
		if(single_role == null) 
			single_role = new ReplicaManagerRole();
		return single_role;
	}
}
