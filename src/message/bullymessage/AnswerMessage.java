package message.bullymessage;

import java.util.UUID;

public class AnswerMessage extends BullyMessage {
	private static UUID messageUUID = UUID.fromString("c38de910-3ff8-11e9-b210-d663bd873d93");

	protected AnswerMessage() {
		super(AnswerMessage.messageUUID);
	}
	
	protected AnswerMessage(UUID uuid) {
		super(uuid);
		// TODO Auto-generated constructor stub
	}


}
