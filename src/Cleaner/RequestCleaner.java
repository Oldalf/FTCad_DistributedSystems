package Cleaner;
import java.util.TimerTask;

import State.rmReplicableState;
import replicaManager.RequestContainer;
import replicaManager.RequestContainer.RequestStage;

	

public class RequestCleaner extends TimerTask {
	private RequestContainer rc;
	private int notConfirmedCounter = 0;
	private boolean isAlive = true;
	public RequestCleaner(RequestContainer requestContainer) {
		this.rc = requestContainer;
	}

	@Override
	public void run() {
		while(isAlive) {
			if(rc.getStage() == RequestStage.ConfirmedToFrontEnd || notConfirmedCounter>5) {
				rmReplicableState state = rmReplicableState.getInstance();
				isAlive = false;
				if(state.Object2Request_state.containsKey(rc.getObject())) {
					state.Object2Request_state.remove(rc.getObject());
					
				}
			}else {
				try {
					notConfirmedCounter++;
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
