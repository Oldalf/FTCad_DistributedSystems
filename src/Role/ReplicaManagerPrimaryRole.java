package Role;

public class ReplicaManagerPrimaryRole extends ReplicaManagerRole{
	private static ReplicaManagerPrimaryRole single_role = null;
	
	
	private ReplicaManagerPrimaryRole() {
		super();
	}
	
	public static ReplicaManagerPrimaryRole getInstance() {
		if(single_role == null) 
			single_role = new ReplicaManagerPrimaryRole();
		return single_role;
	}
}
