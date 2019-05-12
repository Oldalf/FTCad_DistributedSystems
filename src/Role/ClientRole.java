package Role;

public class ClientRole extends AbstractRole{
	private static ClientRole single_role = null;
	
	
	private ClientRole() {
		
	}
	
	public static ClientRole getInstance() {
		if(single_role == null) 
			single_role = new ClientRole();
		return single_role;
	}
}
