package State;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.jgroups.Address;

import Role.FrontendRole;
import frontend.ClientConnection;

public class FrontendState {
	/*
	 * singleton/factory variable
	 */
	private static FrontendState single_instance = null;

	public Address primaryAddress = null;
	public boolean primaryMissing = true;
	public FrontendRole role = FrontendRole.getInstance();
	
	public static ConcurrentHashMap<UUID, ClientConnection> connectedClients = new ConcurrentHashMap<UUID, ClientConnection>();
	
	
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
