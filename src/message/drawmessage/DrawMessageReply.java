package message.drawmessage;

import java.awt.Color;
import java.util.UUID;

import DCAD.GObject;
import State.FrontendState;
import State.ReplicaManagerState;
import message.MessagePayload;
import message.Reply;

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
	}

	protected DrawMessageReply(UUID uuid) {
		super(uuid);
	}

	protected DrawMessageReply(MessagePayload message) {
		super(message);
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

	@Override
	public void executeForFrontend(FrontendState state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeForReplicaManager(ReplicaManagerState state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeForBackupReplicaManager(ReplicaManagerState state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeForPrimaryReplicaManager(ReplicaManagerState state) {
		// TODO Auto-generated method stub

	}

}
