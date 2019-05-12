package DCAD;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.UUID;

import State.clientState;
import message.Message;
import message.Reply;
import message.connectmessage.ConnectMessageReply;
import message.connectmessage.ConnectMessageRequest;
import message.drawmessage.DrawMessageReply;
import message.removedrawmessage.RemoveDrawMessageReply;

public class FrontEndConnection 
{
	// Client
//	private Socket clientSocket = null;
//	private InetAddress clientAddress = null;
//	private int clientPort = 0;
//	private UUID clientID;

	// FrontEnd
//	private InetAddress serverAddress = null;
//	private int serverPort = 0;

	// Variables
	private boolean gotJoinMessage = false;
	//private ConnectMessageRequest connectMessage;
	private ConnectMessageReply replyConnectMessage;
	private Message message = null;
	private InputStream input;
	private OutputStream output;
	private byte[] inputByte;
	private clientState state;
	
	
	public FrontEndConnection() 
	{

	}

	public FrontEndConnection(String hostName, int clientPort) 
	{
		state = clientState.getInstance();
		state.clientPort = clientPort;
		state.serverPort = 25001;

		try {
			state.clientAddress = InetAddress.getLocalHost();
			state.serverAddress = InetAddress.getByName(hostName);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		try {
			state.clientSocket = new Socket(hostName, state.serverPort);
			input = state.clientSocket.getInputStream();
			output = state.clientSocket.getOutputStream();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean handShake(UUID id, GUI gui)
	{
		state = clientState.getInstance();
		state.clientId = id;

		// Skapa ett JoinMessage
		ConnectMessageRequest connectMessage = new ConnectMessageRequest(state.clientId.toString());
		connectMessage.setSenderUUID(state.clientId);
		connectMessage.setReceiverUUID(state.clientId);

		// Marhsal Message och Skicka ett JoinMessage
		try {
			output.write(Message.serializeMessage(connectMessage));
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("CLIENT SKICKADE ETT MEDDELANDE TILL SERVERN!");

		// Loopen är här för att se till att om det skulle komma någon annan typ av meddelande än just "connectMessage" så slängs det och den lyssnar efter nya meddelanden
		while(gotJoinMessage == false)
		{
			// ReceiveMessage, släng om det inte är ett JoinMessage och försök igen
			inputByte = new byte[8192];
			try {
				input.read(inputByte);
			} catch (IOException e) {
				e.printStackTrace();
			}

			replyConnectMessage = (ConnectMessageReply) Message.deserializeMessage(inputByte);

			if(replyConnectMessage instanceof ConnectMessageReply)
			{
				gotJoinMessage = true;
			}
		}

		// To see if its ok to join or not
		if(replyConnectMessage.getReply().equals(Reply.OK))
		{
			return true;	
		}

		else
			return false;

	}

	public void receiveMessages(GUI gui)
	{	
		state = clientState.getInstance();
		/*

		inputByte = new byte[8192];

		try {
			input.read(inputByte);
		} catch (IOException e) {
			e.printStackTrace();
		}

		message = Message.deserializeMessage(inputByte);

		// Kolla vad det är för slags meddelande och gör de beslut utifrån de,
		// såsom att om det är en draw-reply kolla om du ska måla ut det på skärmen
		// eller om det var din draw-request som blev avslagen / declined.


		if(message instanceof DrawMessageReply)
		{
			DrawMessageReply dmr = new DrawMessageReply();
			dmr = (DrawMessageReply)message;


			if(dmr.getReply().equals(Reply.OK))
				gui.addObjectToState(dmr.getObject());
		}
		 */

		// =================================
		// 			   TESTNING!
		// =================================

		byte[] inputByte = new byte[8192];

		try {
			input.read(inputByte);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("KLIENTEN FICK ETT MEDDELANDE!");

		message = Message.deserializeMessage(inputByte);
		
		message.executeForClient(clientState.getInstance());
		gui.callRepaint();
		
//		if(message instanceof DrawMessageReply)
//		{
//
//			DrawMessageReply m = (DrawMessageReply)message;
//
//			if(m.getReply().equals(Reply.OK))
//			{
//				
//				//gui.addObjectToState(m.getObject());
//
//				if(m.getSenderUUID().equals(clientId))
//					//gui.addMyObject(m.getObject());
//			}
//		}

//		else if(message instanceof RemoveDrawMessageReply)
//		{
//
//			RemoveDrawMessageReply m = (RemoveDrawMessageReply)message;
//
//			if(m.getReply().equals(Reply.OK))
//				gui.removeObject(m.getObject());
//
//			gui.repaint();
//		}

	}

	public void sendMessage(Message message)
	{
		state = clientState.getInstance();
		System.out.println(message.toString());

		message.setSenderUUID(state.clientId);

		try {
			output.write(Message.serializeMessage(message));
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("CLIENT SKICKADE ETT MEDDELANDE TILL SERVERN!");
	}
}