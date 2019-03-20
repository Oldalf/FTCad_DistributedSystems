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
			// Plocka bort ett meddelande fr�n clientens meddelandek�
			try {
				message = FrontendState.connectedClients.get(ID).getMessageQueue().take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if(message instanceof DrawMessageReply)
			{

				if(ID.equals(message.getSenderUUID())) // F�r att se till att man inte konstant skickar broadcasts �ven fast man inte var klienten som skickade requesten
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
			
			// Om det �r n�gon annan typ av meddelande �n Draw s� ska det endast skickas till klienten som skickade det, tror jag?
			/*else
			{
				// Kanske att man beh�ver kolla p� meddelandet och utf�ra vissa �ndringar innan man skickar det?

				sendMessageToSelf();
			}*/
		}
	}

	private void broadcast()
	{
		for(ClientConnection entry:FrontendState.connectedClients.values())
		{
			if(!entry.getUUID().equals(ID))	// F�r att se till att man inte l�gger in meddelandet i sin egna k�!
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

