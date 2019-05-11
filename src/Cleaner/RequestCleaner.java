package Cleaner;
import java.util.TimerTask;

import State.rmReplicableState;
import replicaManager.RequestContainer;
import replicaManager.RequestContainer.RequestStage;

	

public class RequestCleaner extends TimerTask {
	private final int NOT_CONFIRMED_LIMIT = 5;
	private final int THREAD_SLEEP_TIME = 10000;
	
	
	private RequestContainer rc;
	private int notConfirmedCounter = 0;
	private boolean isAlive = true;
	public RequestCleaner(RequestContainer requestContainer) {
		this.rc = requestContainer;
	}

	@Override
	public void run() {
		while(isAlive) {
			if(rc.getStage() == RequestStage.ConfirmedToFrontEnd || notConfirmedCounter>NOT_CONFIRMED_LIMIT) {
				rmReplicableState state = rmReplicableState.getInstance();
				isAlive = false;
				if(state.Object2Request_state.containsKey(rc.getObject())) {
					state.Object2Request_state.remove(rc.getObject());
				}
			}else {
				try {
					notConfirmedCounter++;
					Thread.sleep(THREAD_SLEEP_TIME);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
