/**
 *
 * @author brom
 */

// Version 0.7 20:13 2019-03-17

package DCAD;

import java.util.UUID;

public class Cad
{

	static private GUI gui;

	private UUID ID;
	private boolean hasNotConnectedYet = true;
	private FrontEndConnection frontEndConnection = new FrontEndConnection();
	private static Cad client;

	private message.Message Message;

	public static void main(String[] args) {
		client = new Cad();

		// args[0] = HostName och args[1] = Client-port
		client.connectToServer(args[0], Integer.parseInt(args[1]));
	}

	private Cad() 
	{
		ID = UUID.randomUUID();
	}

	private void connectToServer(String hostName, int port)
	{
		frontEndConnection = new FrontEndConnection(hostName, port);

		// Gör en Handshake tills den kommer in på servern
		while(hasNotConnectedYet)
		{
			System.out.println("CLIENT försöker CONNECTA TILL SERVERN!");

			if(frontEndConnection.handShake(ID, gui))
			{
				System.out.println("WELCOME TO THE SERVER!");
				gui = new GUI(750,600, client);
				gui.addToListener();
				hasNotConnectedYet = false;
				
				// TO FIX A BUG, REMOVE LATER
				//frontEndConnection.sendMessage(new DrawMessageRequest(new GObject(Shape.LINE, Color.RED, 1,1,1,1)));
				//frontEndConnection.sendMessage(new RemovedrawMessageRequest());
			}

			else
			{
				System.out.println("COULD NOT CONNECT TO SERVER!");
				ID = UUID.randomUUID();
			}
		}

		listenForMessages();

	}

	private void listenForMessages()
	{
		while(true)
		{
			frontEndConnection.receiveMessages(gui);
		}
	}

	public FrontEndConnection getFrontendConnection()
	{
		return frontEndConnection;
	}
}