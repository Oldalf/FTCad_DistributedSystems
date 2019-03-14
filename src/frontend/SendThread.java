package frontend;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import Server.ClientConnection;
import State.FrontendState;
import message.connectmessage.ConnectMessage;
import message.drawmessage.DrawMessageReply;
import message.Message;

public class SendThread implements Runnable{

	private Socket clientSocket = null;
	private UUID ID;
	private OutputStream output;

	private Message message;

	public SendThread(Socket clientSocket, UUID clientID)
	{
		this.clientSocket = clientSocket;
		ID = clientID;

		try {
			output = this.clientSocket.getOutputStream();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void run() 
	{
		while(true)
		{
			// Plocka bort ett meddelande från clientens meddelandekö
			try {
				message = FrontendState.connectedClients.get(ID).getMessageQueue().take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			// Draw reply
			if(message.getUuid().equals(UUID.fromString("77bf05fc-40f5-11e9-b210-d663bd873d93")))
			{
				// Kolla om det är OK eller ICKE-OK, alltså om det ska eller inte ska ritas ut,
				// om det ska skicka till alla klienter annars skicka endast tillbaka ett meddelande
				// till klienten om att det blev ett avslag eller nått.

				DrawMessageReply dmr = (DrawMessageReply)message;
				
				if(!ID.equals(message.senderID())) // För att se till att man inte konstant skickar broadcasts även fast man inte var klienten som skickade requesten
				{
					broadcast();
				}

				// Skicka endast meddelandet till sig själv!
				else
				{
					sendMessageToSelf();
				}
			}

			// Om det är någon annan typ av meddelande än Draw så ska det endast skickas till klienten som skickade det, tror jag?
			else
			{
				// Kanske att man behöver kolla på meddelandet och utföra vissa ändringar innan man skickar det?
				
				sendMessageToSelf();
			}
		}
	}

	private void broadcast()
	{
		for(ClientConnection entry:FrontendState.connectedClients.values())
		{
			if(!entry.getUUID().equals(ID))	// För att se till att man inte lägger in meddelandet i sin egna kö!
			{
				try {
					entry.getMessageQueue().put(message);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}

		sendMessageToSelf();
	}

	private void sendMessageToSelf()
	{
		try {
			output.write(message.serialize());
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

