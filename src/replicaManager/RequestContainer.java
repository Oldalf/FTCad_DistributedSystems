package replicaManager;

import DCAD.GObject;

public class RequestContainer {
	enum requestType {
		Draw, Remove
	}

	enum RequestStage {
		Received, AddedToMyState, SentToBackup, ConfrimedByBackup, SentToFrontEnd
	}

	private GObject object;
	private requestType type;
	private RequestStage stage;
	private long lastTouched;

	public RequestContainer(GObject object, requestType type, RequestStage stage, long lastTouched) {
		this.object = object;
		this.type = type;
		this.stage = stage;
		this.lastTouched = lastTouched;
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

}
