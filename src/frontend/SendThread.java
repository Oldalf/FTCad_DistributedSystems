package frontend;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.UUID;

import State.FrontendState;
import message.Message;
import message.drawmessage.DrawMessageReply;
import message.removedrawmessage.RemoveDrawMessageReply;

public class SendThread implements Runnable{

	private Socket clientSocket = null;
	private UUID ID;
	private OutputStream output;

	private volatile boolean isAlive = true;
	
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
		while(isAlive)
		{
			// Plocka bort ett meddelande från clientens meddelandekö
			try {
				message = FrontendState.connectedClients.get(ID).getMessageQueue().take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if(message instanceof DrawMessageReply)
			{

				if(ID.equals(message.getSenderUUID())) // För att se till att man inte konstant skickar broadcasts även fast man inte var klienten som skickade requesten
				{
					broadcast();
				}

				else
				{
					sendMessageToSelf();
				}
			}

			else if(message instanceof RemoveDrawMessageReply)
			{
				broadcast();
			}
			
			// Om det är någon annan typ av meddelande än Draw så ska det endast skickas till klienten som skickade det, tror jag?
			/*else
			{
				// Kanske att man behöver kolla på meddelandet och utföra vissa ändringar innan man skickar det?

				sendMessageToSelf();
			}*/
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
			output.write(Message.serializeMessage(message));
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

