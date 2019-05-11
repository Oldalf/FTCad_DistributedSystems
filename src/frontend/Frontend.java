package frontend;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

import State.FrontendState;
import message.Message;
import message.Reply;
import message.connectmessage.ConnectMessageReply;
import message.connectmessage.ConnectMessageRequest;

public class Frontend {

	private ServerSocket serverSocket = null;
	private Socket clientSocket = null;

	private InputStream input;
	private OutputStream output;
	private ConnectMessageReply connectMessage; 
	
	private byte[] inputByte;
	
	private LinkedBlockingQueue<Message> queueBetweenRThreadAndRmCom = new LinkedBlockingQueue<Message>();
	
	public static void main(String[] args) 
	{
		Frontend frontend = new Frontend(Integer.parseInt(args[0]));
		frontend.running();
	}

	public Frontend(int port)
	{
		try {
			this.serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Thread rmCommunicationThread = new Thread(new RmCommunication(queueBetweenRThreadAndRmCom));
		rmCommunicationThread.start();
	}

	private void running()
	{
		while(true)
		{
			System.out.println("Waiting for Messages...");

			receiveMessage();
			
			// Unmarshal Message
			Message message = Message.deserializeMessage(inputByte);
			
			ConnectMessageRequest rm = (ConnectMessageRequest)message;
			
			if(!FrontendState.connectedClients.containsKey(UUID.fromString(rm.getID())))
			{
				FrontendState.connectedClients.put(UUID.fromString(rm.getID()), new ClientConnection(UUID.fromString(rm.getID()), clientSocket.getInetAddress(), clientSocket.getPort()));
				//Thread r = new Thread(new ReceiveThread(clientSocket, UUID.fromString(rm.getID())), queueBetweenRThreadAndRmCom);
				Thread r = new Thread(new ReceiveThread(clientSocket, message.getSenderUUID(), queueBetweenRThreadAndRmCom));
				Thread s = new Thread(new SendThread(clientSocket, UUID.fromString(rm.getID())));

				connectMessage = new ConnectMessageReply(Reply.OK);

				try {
					output.write(Message.serializeMessage(connectMessage));
					output.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}

				r.start();
				s.start();
			}

			
			// Annars skicka bara tillbaka ett meddelande att "id är upptaget" / får inte ansluta
			else
			{
				connectMessage = new ConnectMessageReply(Reply.CHANGEUUID);

				try {
					output.write(Message.serializeMessage(connectMessage));
					output.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void receiveMessage()
	{
		try {
			clientSocket = serverSocket.accept();
			output = clientSocket.getOutputStream();
			input = clientSocket.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}

		inputByte = new byte[8192];

		try {
			input.read(inputByte);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("SERVER FICK ETT MEDDELANDE!");
	}
}