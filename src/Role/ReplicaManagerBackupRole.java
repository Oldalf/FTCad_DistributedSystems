package Role;

public class ReplicaManagerBackupRole extends ReplicaManagerRole {
	private static ReplicaManagerBackupRole single_role = null;
	
	
	private ReplicaManagerBackupRole() {
		super();
	}
	
	public static ReplicaManagerBackupRole getInstance() {
		if(single_role == null) 
			single_role = new ReplicaManagerBackupRole();
		return single_role;
	}
}
