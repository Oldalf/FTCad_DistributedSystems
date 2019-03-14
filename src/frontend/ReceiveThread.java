package frontend;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

import State.FrontendState;
import message.Message;

public class ReceiveThread implements Runnable{

	private Socket clientSocket = null;
	private UUID ID;
	private InputStream input;
	private LinkedBlockingQueue<Message> toRmComQueue;

	public ReceiveThread(Socket clientSocket, UUID clientID, LinkedBlockingQueue<Message> queue)
	{
		this.clientSocket = clientSocket;
		ID = clientID;
		this.toRmComQueue = queue;
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

		sendMessageToReplica(inputByte);
	}
	
	private void sendMessageToReplica(byte[] b)
	{
		// Sending the message to the replica-Managers message queue
		try {
			toRmComQueue.put(Message.deserializeMessage(b));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

