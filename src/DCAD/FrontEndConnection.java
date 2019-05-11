package DCAD;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.UUID;

import message.Message;
import message.Reply;
import message.connectmessage.ConnectMessageReply;
import message.connectmessage.ConnectMessageRequest;

public class FrontEndConnection 
{
	// Client
	private Socket clientSocket = null;
	private InetAddress clientAddress = null;
	private int clientPort = 0;
	private UUID clientID;

	// FrontEnd
	private InetAddress serverAddress = null;
	private int serverPort = 0;

	// Variables
	private boolean gotJoinMessage = false;
	//private ConnectMessageRequest connectMessage;
	private ConnectMessageReply replyConnectMessage;
	private Message message;
	private InputStream input;
	private OutputStream output;
	private byte[] inputByte;

	public FrontEndConnection() 
	{

	}

	public FrontEndConnection(String hostName, int clientPort) 
	{
		this.clientPort = clientPort;
		serverPort = 25001;

		try {
			clientAddress = InetAddress.getLocalHost();
			serverAddress = InetAddress.getByName(hostName);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		try {
			clientSocket = new Socket(hostName, serverPort);
			input = clientSocket.getInputStream();
			output = clientSocket.getOutputStream();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean handShake(UUID id)
	{
		clientID = id;

		// Skapa ett JoinMessage
		ConnectMessageRequest connectMessage = new ConnectMessageRequest(clientID.toString());

		// Marhsal Message och Skicka ett JoinMessage
		try {
			output.write(Message.serializeMessage(connectMessage));
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("CLIENT SKICKADE ETT MEDDELANDE TILL SERVERN!");

		// Loopen �r h�r f�r att se till att om det skulle komma n�gon annan typ av meddelande �n just "connectMessage" s� sl�ngs det och den lyssnar efter nya meddelanden
		while(gotJoinMessage == false)
		{
			// ReceiveMessage, sl�ng om det inte �r ett JoinMessage och f�rs�k igen
			inputByte = new byte[8192];
			try {
				input.read(inputByte);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			UUID tempID = Message.getUUIDFromJSONObject(inputByte);
			if(Message.defineMessageClassWithUUID(tempID).equals("ConnectMessageReply"))
			{
				replyConnectMessage = (ConnectMessageReply) Message.deserializeMessage(inputByte);
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

	public void receiveMessages()
	{	
		inputByte = new byte[8192];

		try {
			input.read(inputByte);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		message = Message.deserializeMessage(inputByte);

		// Kolla vad det �r f�r slags meddelande och g�r de beslut utifr�n de,
		// s�som att om det �r en draw-reply kolla om du ska m�la ut det p� sk�rmen
		// eller om det var din draw-request som blev avslagen / declined.

	}
}