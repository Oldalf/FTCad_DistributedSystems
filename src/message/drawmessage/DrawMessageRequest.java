package message.drawmessage;

import java.awt.Color;
import java.util.UUID;

import DCAD.GObject;
import Role.ClientRole;
import State.FrontendState;
import State.ReplicaManagerState;
import State.clientState;
import message.MessagePayload;
import replicaManager.ReplicaManagerMessageContainer;
import replicaManager.RequestContainer;
import replicaManager.RequestContainer.RequestStage;
import replicaManager.RequestContainer.requestType;

public class DrawMessageRequest extends DrawMessage {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static UUID messageUUID = UUID.fromString("77bf00a2-40f5-11e9-b210-d663bd873d93");

	private int x;
	private int y;
	private int width;
	private int height;
	private DCAD.Shape s;
	private Color c;

	private GObject object = null;

	public DrawMessageRequest() {
		super(DrawMessageRequest.messageUUID);
		createGObject();
	}

	protected DrawMessageRequest(UUID uuid) {
		super(uuid);
		createGObject();
	}

	protected DrawMessageRequest(MessagePayload message) {
		super(message);
		createGObject();
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public DCAD.Shape getS() {
		return s;
	}

	public void setS(DCAD.Shape s) {
		this.s = s;
	}

	public Color getC() {
		return c;
	}

	public void setC(Color c) {
		this.c = c;
	}

	public DrawMessageRequest(GObject object) {
		super(DrawMessageRequest.messageUUID);
		this.object = object;
	}

	public void setObject(GObject object) {
		this.object = object;
	}

	public GObject getObject() {
		return this.object; // new GObject(s,c,x,y,width,height);
	}
	
	private void createGObject() {
		this.object = new GObject(s,c,x,y,width,height);
	}

	@Override
	public void executeForFrontend(FrontendState state) {

	}

	@Override
	public void executeForReplicaManager(ReplicaManagerState state) {
	

	}

	@Override
	public void executeForBackupReplicaManager(ReplicaManagerState state) {
		RequestContainer rq = new RequestContainer(object,requestType.Draw, RequestStage.ConfrimedByBackup);
		if(!state.rpState.Object2Request_state.contains(object)) {
			// the object does not exist in our state, add it
			state.rpState.Object2Request_state.put(object, rq);
		}
		// Send a return to primary that we're okey with the object existing.(ack)
		state.rpState.ReadyToSendRequests.add(rq);

	}

	@Override
	public void executeForPrimaryReplicaManager(ReplicaManagerState state) {
		RequestContainer rq = new RequestContainer(object,requestType.Draw, RequestStage.Received);
		if(state.rpState.Object2Request_state.contains(object)) {
			// the request already existed.
			rq = state.rpState.Object2Request_state.get(object);
		} else {
			state.rpState.cadState.add(object);
			rq.setStage(RequestStage.AddedToMyState);			
		}
		state.responseList.add(new ReplicaManagerMessageContainer(this,null,"DrawMessageRequest"));
		rq.setStage(RequestStage.SentToBackup);
	}
	@Override
	public void executeForClient(clientState state) {
		if(state.role instanceof ClientRole) {
			state.myObjects.add(this.object);
		}
		else {
			throw new IllegalStateException();
		}
		
	}

}
