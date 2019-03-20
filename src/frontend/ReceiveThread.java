package frontend;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

import message.Message;

public class ReceiveThread implements Runnable
{

	private Socket clientSocket = null;
	private UUID ID;
	private InputStream input;

	private volatile boolean isAlive = true;
	
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
		while(isAlive)
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

		System.out.println("RECEIVER THREAD FICK ETT MEDDELANDE!");
		
		sendMessageToReplica(inputByte);
		
		//==============================================================
		// FÖR TESTNING!
		// Detta är vad typ sender tråden ska göra samt lite vad förmodligen messageHandler tråden ska göra (omvanligen av meddelanden kanske?).
		
		/*
		
		Message m = Message.deserializeMessage(inputByte);
		
		if(m instanceof DrawMessageRequest)
		{
			DrawMessageRequest dmr = (DrawMessageRequest)m;
			DrawMessageReply dm = new DrawMessageReply(dmr.getObject(), Reply.OK);
			dm.setSenderUUID(dmr.getSenderUUID());	// FÅR INTE GLÖMMA ATT SÄTTA ID:et IGEN OM DU SKAPAR OM MEDDELANDEN!
			
			try {
				FrontendState.connectedClients.get(ID).getMessageQueue().put(dm);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		
		else if(m instanceof RemovedrawMessageRequest)
		{
			RemovedrawMessageRequest rdr = (RemovedrawMessageRequest)m;
			RemovedrawMessageReply dm = new RemovedrawMessageReply();
		}
		
		
		*/
		
		
		//DrawMessageRequest m = (DrawMessageRequest) Message.deserializeMessage(inputByte);
		
		//DrawMessageReply dm = new DrawMessageReply();
		
		//dm = new DrawMessageReply(m.getObject(), Reply.OK);

		/*
		
		try {
			output.write(Message.serializeMessage(dm));
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		*/
		
		//===============================================================
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

