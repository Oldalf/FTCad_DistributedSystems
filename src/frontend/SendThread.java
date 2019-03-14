package frontend;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import message.connectmessage.ConnectMessage;
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
			// Plocka bort ett meddelande fr�n clientens meddelandek�
			// Kolla vilket typ av meddelande det �r (t.ex draw)

			// message = det meddelandet som tas ifr�n k�n
			// Kanske att det ska skickas till alla clienter i "connectedClients" hashmappen? lite som en broadcast?
			
			//output.write(message);
			//output.flush();

			// Tror inte att writer beh�ver g�ra mycket mer d� den ska inte utf�ra n�gra beslut som i TCP-Chat 
			// utan den endast skickar meddelanden som Replica Managern redan har tagit beslut p�.
		}
	}
}

