package State;

import org.jgroups.Address;

import Role.AbstractRole;
import Role.FrontendRole;

public class FrontendState {
	/*
	 * singleton/factory variable
	 */
	private static FrontendState single_instance = null;

	public Address primaryAddress;
	public boolean primaryMissing;
	public AbstractRole role = FrontendRole.getInstance();
	/*
	 * Concurrent hashmap with clients.
	 */
	
	protected FrontendState() {

	}

	public static FrontendState getInstance() {
		if (single_instance == null)
			single_instance = new FrontendState();
		return single_instance;
	}
}
