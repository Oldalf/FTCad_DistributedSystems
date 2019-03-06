package frontend;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.UUID;

public class SendThread implements Runnable{

	private Socket clientSocket = null;
	private UUID ID;
	private InputStream input;
	private OutputStream output;

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

		}
	}
}

