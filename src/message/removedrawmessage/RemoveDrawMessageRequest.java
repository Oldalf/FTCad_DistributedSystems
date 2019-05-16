package message.removedrawmessage;

import java.util.UUID;

import DCAD.GObject;
import Role.ClientRole;
import Role.FrontendRole;
import Role.ReplicaManagerBackupRole;
import Role.ReplicaManagerPrimaryRole;
import Role.ReplicaManagerRole;
import State.FrontendState;
import State.ReplicaManagerState;
import State.clientState;
import message.MessagePayload;
import replicaManager.RequestContainer;
import replicaManager.RequestContainer.RequestStage;
import replicaManager.RequestContainer.requestType;

public class RemoveDrawMessageRequest extends RemoveDrawMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static UUID messageUUID = UUID.fromString("dfaa8aec-489d-11e9-8646-d663bd873d93");

	private GObject object;

	public RemoveDrawMessageRequest() {
		super(RemoveDrawMessageRequest.messageUUID);
	}

	public RemoveDrawMessageRequest(GObject object) {
		super(RemoveDrawMessageRequest.messageUUID);
		this.object = object;
	}

	protected RemoveDrawMessageRequest(UUID uuid) {
		super(uuid);
	}

	protected RemoveDrawMessageRequest(MessagePayload message) {
		super(message);
	}

	public void setObject(GObject object) {
		this.object = object;
	}

	public GObject getObject() {
		return this.object;
	}

	@Override
	public void executeForFrontend(FrontendState state) {
		if (state.role instanceof FrontendRole) {

		} else {
			throw new IllegalStateException();
		}

	}

	@Override
	public void executeForReplicaManager(ReplicaManagerState state) {
		// if (state.role instanceof ReplicaManagerRole) {
		
		// } else {
		// throw new IllegalStateException();
		// }
	}

	@Override
	public void executeForBackupReplicaManager(ReplicaManagerState state) {
		// if (state.role instanceof ReplicaManagerBackupRole) {
		RequestContainer rq = new RequestContainer(object, requestType.Remove, RequestStage.ConfrimedByBackup);
		if (!state.rpState.Object2Request_state.contains(object)) {
			// the object does not exist in our requestmap, add it
			state.rpState.Object2Request_state.put(object, rq);
		} else {
			rq = state.rpState.Object2Request_state.get(object);
			// make sure the type is correct.
			rq.setType(requestType.Remove);
			// update stage.
			rq.setStage(RequestStage.ConfrimedByBackup);
		}
		if (state.rpState.cadState.contains(object)) {
			// if we had the object in our state remove it.
			state.rpState.cadState.remove(object);
		}
		// Send a return to primary that we're okey with being removed (ack).
		state.rpState.ReadyToSendRequests.add(rq);
		// } else {
		// throw new IllegalStateException();
		// }
	}

	@Override
	public void executeForPrimaryReplicaManager(ReplicaManagerState state) {
		// if (state.role instanceof ReplicaManagerPrimaryRole) {
		RequestContainer rq = new RequestContainer(object, requestType.Remove, RequestStage.ConfrimedByBackup);
		if (!state.rpState.Object2Request_state.contains(object)) {
			// the object does not exist in our requestmap, add it
			state.rpState.Object2Request_state.put(object, rq);
		} else {
			rq = state.rpState.Object2Request_state.get(object);
			// make sure the type is correct.
			rq.setType(requestType.Remove);
			// update stage.
			rq.setStage(RequestStage.ConfrimedByBackup);
		}
		if (state.rpState.cadState.contains(object)) {
			// if we had the object in our state remove it.
			state.rpState.cadState.remove(object);
		}
		// Send a return to primary that we're okey with the object existing.(ack)
		state.rpState.ReadyToSendRequests.add(rq);
		// } else {
		// throw new IllegalStateException();
		// }
	}

	@Override
	public void executeForClient(clientState state) {
		if (state.role instanceof ClientRole) {
			state.myObjects.remove(this.object);
		} else {
			throw new IllegalStateException();
		}

	}

}
