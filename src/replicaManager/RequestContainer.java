package replicaManager;

import java.util.Timer;
import java.util.UUID;

import Cleaner.RequestCleaner;
import DCAD.GObject;

public class RequestContainer {
	int REQUEST_TIMEOUT_MS = 600000; //10 min in milisec.. Used for timerTask. 
	public enum requestType {
		Draw, Remove, State
	}


	public enum RequestStage {
		Received, AddedToMyState, SentToBackup, ConfrimedByBackup, ConfirmedToFrontEnd
	}

	private GObject object;
	private requestType type;
	private RequestStage stage;
	private Timer timer;
	private long lastTouched;
	private UUID requester;
	

	public RequestContainer(GObject object, requestType type, RequestStage stage) {
        this.object = object;
        this.type = type;
        this.stage = stage;
        startTimer();
        setLastTouched();
    }
    
    public GObject getObject() {
        return object;
    }

    public void setObject(GObject object) {
        this.object = object;
        setLastTouched();        
    }

    public requestType getType() {
        return type;
    }

    public void setType(requestType type) {
        this.type = type;
        setLastTouched();
    }

    public RequestStage getStage() {
        return stage;
    }

    public void setStage(RequestStage stage) {
        this.stage = stage;
        setLastTouched();
    }

    public long getLastTouched() {
        return lastTouched;
    }

    private void setLastTouched() {
        this.lastTouched = System.currentTimeMillis() / 1000;
        refreshTimer();
    }
	
	public UUID getRequester() {
		return requester;
	}

	public void setRequester(UUID requester) {
		this.requester = requester;
	}

	public void startTimer() {
		this.timer = new Timer();
		this.timer.schedule(new RequestCleaner(this), REQUEST_TIMEOUT_MS);
	}
	
	public void refreshTimer(){
		this.timer.cancel();
		this.timer.schedule(new RequestCleaner(this), REQUEST_TIMEOUT_MS);
	}
	
	

}
