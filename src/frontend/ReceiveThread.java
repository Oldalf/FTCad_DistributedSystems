package frontend;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.UUID;

public class ReceiveThread implements Runnable{

	private Socket clientSocket = null;
	private UUID ID;
	private InputStream input;

	public ReceiveThread(Socket clientSocket, UUID clientID)
	{
		this.clientSocket = clientSocket;
		ID = clientID;

		try {
			input = this.clientSocket.getInputStream();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void run() 
	{
		while(true)
		{
			receiveClientMessage();
		}
	}

	private void receiveClientMessage()
	{
		byte[] b = new byte[8192];
		try {
			input.read(b);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// UnMarshal-Message or just Read the type
		// Put the message in the right LinkedBlockingQueue- 
		// depending on the UUID on the hashmap 
		//			ELLER
		// Lägg in det i replicaMessageQueue som endast Backend-Tråden använder?

	}
}

