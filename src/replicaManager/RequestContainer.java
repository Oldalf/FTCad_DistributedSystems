package replicaManager;

import java.util.Timer;

import Cleaner.RequestCleaner;
import DCAD.GObject;

public class RequestContainer {
	int REQUEST_TIMEOUT_MS = 600000; //10 min in milisec.. Used for timerTask. 
	enum requestType {
		Draw, Remove
	}

	enum RequestStage {
		Received, AddedToMyState, SentToBackup, ConfrimedByBackup, SentToFrontEnd
	}

	private GObject object;
	private requestType type;
	private RequestStage stage;
	private Timer timer;
	private long lastTouched;
	

//	public RequestContainer(GObject object, requestType type, RequestStage stage, long lastTouched) {
//		this.object = object;
//		this.type = type;
//		this.stage = stage;
//		this.lastTouched = lastTouched;
//	}

	public RequestContainer(GObject object, requestType type, RequestStage stage) {
		this.object = object;
		this.type = type;
		this.stage = stage;
	}
	
	

	public GObject getObject() {
		return object;
	}

	public void setObject(GObject object) {
		this.object = object;
	}

	public requestType getType() {
		return type;
	}

	public void setType(requestType type) {
		this.type = type;
	}

	public RequestStage getStage() {
		return stage;
	}

	public void setStage(RequestStage stage) {
		this.stage = stage;
	}

	public long getLastTouched() {
		return lastTouched;
	}

	public void setLastTouched(long lastTouched) {
		this.lastTouched = lastTouched;
	}
	public void startTimer() {
		this.timer = new Timer();
		this.timer.schedule(new RequestCleaner(this), REQUEST_TIMEOUT_MS);
	}

}
