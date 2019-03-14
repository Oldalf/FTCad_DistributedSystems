package DCAD;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.UUID;

import message.ConnectMessage;
import message.Message;
import message.MessageStatus;

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
	private ConnectMessage connectMessage;
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
		connectMessage = new ConnectMessage(clientID, MessageStatus.REQUEST);

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

			// Tror denna kollar om UUID:et på meddelandet är samma UUID som används för ConnectMessage
			if(Message.getUUIDFromJSONObject(inputByte).equals(UUID.fromString("fe28ead0-3b38-11e9-b210-d663bd873d93")) && 
					connectMessage.getMessageStatus().equals(MessageStatus.REPLY))
			{
				// Unmarshal Message
				connectMessage.deserialize(inputByte);
				gotJoinMessage = true;
			}
		}

		// To see if its ok to join or not
		if(connectMessage.getTextMessage().equals("OK"))
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

		connectMessage.deserialize(inputByte);

		// Kolla vad det är för slags meddelande och gör de beslut utifrån de,
		// såsom att om det är en draw-reply kolla om du ska måla ut det på skärmen
		// eller om det var din draw-request som blev avslagen / declined.

	}
}