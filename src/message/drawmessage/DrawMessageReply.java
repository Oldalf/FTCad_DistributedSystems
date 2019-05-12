package message.drawmessage;

import java.awt.Color;
import java.net.Authenticator.RequestorType;
import java.util.UUID;

import DCAD.GObject;
import Role.ClientRole;
import Role.FrontendRole;
import State.FrontendState;
import State.ReplicaManagerState;
import State.clientState;
import frontend.ClientConnection;
import message.MessagePayload;
import message.Reply;
import replicaManager.RequestContainer;
import replicaManager.RequestContainer.RequestStage;
import replicaManager.RequestContainer.requestType;

public class DrawMessageReply extends DrawMessage {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static UUID messageUUID = UUID.fromString("77bf05fc-40f5-11e9-b210-d663bd873d93");

	private int x;
	private int y;
	private int width;
	private int height;
	private DCAD.Shape s;
	private Color c;

	private GObject object = new GObject();

	private Reply reply;

	public DrawMessageReply() {
		super(DrawMessageReply.messageUUID);
		createGObject();
	}

	protected DrawMessageReply(UUID uuid) {
		super(uuid);
		createGObject();
	}

	protected DrawMessageReply(MessagePayload message) {
		super(message);
		createGObject();
	}

	public DrawMessageReply(GObject object, Reply reply) {
		super(DrawMessageReply.messageUUID);
		this.reply = reply;
		this.object = object;
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

	public void setReply(Reply reply) {
		this.reply = reply;
	}

	public Reply getReply() {
		return this.reply;
	}

	public void setObject(GObject object) {
		this.object = object;
	}

	public GObject getObject() {
		return this.object;// new GObject(s,c,x,y,width,height);
	}

	private void createGObject() {
		this.object = new GObject(s, c, x, y, width, height);
	}

	@Override
	public void executeForFrontend(FrontendState state) {
		if (state.role instanceof FrontendRole) {
			for (ClientConnection cc : state.connectedClients.values()) {
				cc.getMessageQueue().add(this);
			}
		}
	}

	@Override
	public void executeForReplicaManager(ReplicaManagerState state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeForBackupReplicaManager(ReplicaManagerState state) {
		// check if it exists in our state (it should do)
		RequestContainer rq = null;
		if (state.rpState.Object2Request_state.containsKey(object)) {
			// get request container
			rq = state.rpState.Object2Request_state.get(object);
			// update stage.
			rq.setStage(RequestStage.ConfirmedToFrontEnd);
		} else {
			// somehow it doesn't exists, add it and update it
			rq = new RequestContainer(object, requestType.Draw, RequestStage.ConfirmedToFrontEnd);
			state.rpState.Object2Request_state.put(object, rq);
		}
		// add it to the queue that sends it back to primary.
		state.rpState.ReadyToSendRequests.add(rq);

	}

	@Override
	public void executeForPrimaryReplicaManager(ReplicaManagerState state) {
		// check if it exists in our state (it should do)
		RequestContainer rq = null;
		if (state.rpState.Object2Request_state.containsKey(object)) {
			// get request container
			rq = state.rpState.Object2Request_state.get(object);
			// update stage.
			rq.setStage(RequestStage.ConfirmedToFrontEnd);
		} else {
			// somehow it doesn't exists, add it and update it
			rq = new RequestContainer(object, requestType.Draw, RequestStage.ConfirmedToFrontEnd);
			state.rpState.Object2Request_state.put(object, rq);
		}
		// add it to the queue that sends it back to primary.
		state.rpState.ReadyToSendRequests.add(rq);
	}
	@Override
	public void executeForClient(clientState state) {
		if(state.role instanceof ClientRole) {
			state.globalObjectList.add(this.object);
		}
		else {
			throw new IllegalStateException();
		}
		
	}

}
