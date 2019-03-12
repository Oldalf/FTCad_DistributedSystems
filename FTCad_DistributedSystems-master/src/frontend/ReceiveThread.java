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
		byte[] inputByte = new byte[8192];

		try {
			input.read(inputByte);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Här ska den skicka meddelandet (input byten) till Replica-Manager tråden!

	}
}

