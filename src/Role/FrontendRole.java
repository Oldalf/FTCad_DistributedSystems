package Role;

public class FrontendRole extends AbstractRole {
	private static FrontendRole single_role = null;
	
	
	private FrontendRole() {
		
	}
	
	public static FrontendRole getInstance() {
		if(single_role == null) 
			single_role = new FrontendRole();
		return single_role;
	}
}
