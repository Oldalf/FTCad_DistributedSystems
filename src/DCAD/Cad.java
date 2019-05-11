/**
 *
 * @author brom
 */

// Version 0.4 16:25 2019-03-12

package DCAD;

import java.util.UUID;

public class Cad {
	static private GUI gui;

	private UUID ID;
	private boolean hasNotConnectedYet = true;
	private FrontEndConnection frontEndConnection = new FrontEndConnection();

	public static void main(String[] args) {
		Cad c = new Cad();

		// args[0] = HostName och args[1] = Client-port
		c.connectToServer(args[0], Integer.parseInt(args[1]));
	}

	private Cad() 
	{
		ID = UUID.randomUUID();
	}

	private void connectToServer(String hostName, int port)
	{
		frontEndConnection = new FrontEndConnection(hostName, port);

		// Gör en Handshake tills den kommer in på servern!
		while(hasNotConnectedYet)
		{
			System.out.println("CLIENT FÖRSÖKER CONNECTA TILL SERVERN!");

			if(frontEndConnection.handShake(ID))
			{
				System.out.println("WELCOME TO THE SERVER!");
				gui = new GUI(750,600);
				gui.addToListener();
				hasNotConnectedYet = false;
			}

			else
			{
				System.out.println("COULD NOT CONNECT TO SERVER!");
				ID = UUID.randomUUID();
			}
		}

		while(true)
		{
			frontEndConnection.receiveMessages();
		}
	}
}