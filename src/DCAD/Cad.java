/**
 *
 * @author brom
 */

package DCAD;

import java.util.UUID;

import message.Message;
import message.MessageStatus;
import message.connectmessage.ConnectMessage;

public class Cad {
	static private GUI gui = new GUI(750,600);

	private UUID ID;
	private boolean hasNotConnectedYet = true;
	private FrontEndConnection frontEndConnection = new FrontEndConnection();
	
	private Message message = null;
	
	public static void main(String[] args) {
		gui.addToListener();
		Cad c = new Cad();

		// args[0] = HostName och args[1] = Client-port
		c.connectToServer(args[0], Integer.parseInt(args[1]));
	}

	private Cad() 
	{
		ID = UUID.randomUUID(); // Vet inte om detta är vad vi skulle göra eller om vi skulle göra statiska!
	}

	private void connectToServer(String hostName, int port)
	{
		frontEndConnection = new FrontEndConnection(hostName, port);

		message.equals(new ConnectMessage("Oscar", MessageStatus.REQUEST));
		
		// Gör en Handshake tills den kommer in på servern!
		while(hasNotConnectedYet)
		{
			if(frontEndConnection.handShake(ID))
			{
				System.out.println("WELCOME TO THE SERVER!");
				hasNotConnectedYet = false;
			}
			else
			{
				System.out.println("COULD NOT CONNECT TO SERVER!");
				ID = UUID.randomUUID();
			}
		}
	}
}