package DCAD;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.UUID;

import message.JoinMessage;

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
	private JoinMessage jMessage = new JoinMessage();	// Ska joinMessage vara protected? och är det såhär vi ska göra?
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

		// Marhsal Message // Vet inte hur det fungerar med Jgroups så väntar med det

		// Skicka ett JoinMessage
		output.write(/* Marshaled Message */);
		output.flush();

		// Loopen är här för att fixa "problemet" med att andra slags meddelanden kan komma medans man väntar på receive.
		while(!gotJoinMessage)
		{
			// ReceiveMessage(), släng om det inte är ett JoinMessage och försök igen
			inputByte = new byte[8192];
			input.read(inputByte);		// Vet inte om det är såhär vi ska göra med Jgroups eller inte?

			// om det är ett JoinMessage-type -> gotJoinMessage = true; annars loopa om igen!
		}

		// Unmarshal Message

		/*
		 * if(message = OK)
		 * 		then return true typ
		 * 
		 * else
		 * 		return false;
		 * 
		 * */
		return false;	// Bara här för att inte få error!
	}
}