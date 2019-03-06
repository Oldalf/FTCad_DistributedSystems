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
	private JoinMessage jMessage = new JoinMessage();	// Ska joinMessage vara protected? och �r det s�h�r vi ska g�ra?
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

		// Marhsal Message // Vet inte hur det fungerar med Jgroups s� v�ntar med det

		// Skicka ett JoinMessage
		output.write(/* Marshaled Message */);
		output.flush();

		// Loopen �r h�r f�r att fixa "problemet" med att andra slags meddelanden kan komma medans man v�ntar p� receive.
		while(!gotJoinMessage)
		{
			// ReceiveMessage(), sl�ng om det inte �r ett JoinMessage och f�rs�k igen
			inputByte = new byte[8192];
			input.read(inputByte);		// Vet inte om det �r s�h�r vi ska g�ra med Jgroups eller inte?

			// om det �r ett JoinMessage-type -> gotJoinMessage = true; annars loopa om igen!
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
		return false;	// Bara h�r f�r att inte f� error!
	}
}