package Cleaner;
import java.util.TimerTask;

import State.rmReplicableState;
import replicaManager.RequestContainer;

	

public class RequestCleaner extends TimerTask {
	private RequestContainer rc;
	public RequestCleaner(RequestContainer requestContainer) {
		this.rc = requestContainer;
	}

	@Override
	public void run() {
		rmReplicableState state = rmReplicableState.getInstance();
		if(state.Object2Request_state.containsKey(rc.getObject())) {
			state.Object2Request_state.remove(rc.getObject());
		}
		
		
		
	}
	

}
