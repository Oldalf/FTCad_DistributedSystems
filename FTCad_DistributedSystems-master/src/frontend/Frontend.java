package frontend;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import message.ConnectMessage;
import message.Message;
import message.MessageStatus;

public class Frontend {

	private ServerSocket serverSocket = null;
	private Socket clientSocket = null;

	private InputStream input;
	private OutputStream output;
	private ConcurrentHashMap<UUID, ClientConnection> connectedClients = new ConcurrentHashMap<UUID, ClientConnection>();

	private ConnectMessage connectMessage = null;
	private boolean gotJoinMessage = false;

	private byte[] inputByte;

	public static void main(String[] args) 
	{
		Frontend frontend = new Frontend(Integer.parseInt(args[0]));
		frontend.running();
	}

	public Frontend(int port)
	{
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Thread rmCommunicationThread = new Thread(new RmCommunication());
		rmCommunicationThread.start();
	}

	private void running()
	{
		while(true)
		{
			System.out.println("Waiting for Messages...");

			// Här för att se till att den inte tar emot andra slags meddelanden än ConnectMessage
			while(gotJoinMessage == false)
			{

				receiveMessage();

				// Tror denna kollar om UUID:et på meddelandet är samma UUID som används för ConnectMessage
				if(Message.getUUIDFromJSONObject(inputByte).equals(UUID.fromString("fe28ead0-3b38-11e9-b210-d663bd873d93")) && 
						connectMessage.getMessageStatus().equals(MessageStatus.REQUEST))
				{
					// Unmarshal Message
					connectMessage.deserialize(inputByte);
					gotJoinMessage = true;
				}
			}

			if(!connectedClients.containsKey(connectMessage.getName()))
			{
				// OBS! getName() returnerar inte ett namn (string) utan clientens UUID!
				connectedClients.put(connectMessage.getName(), new ClientConnection(connectMessage.getName(), clientSocket.getInetAddress(), clientSocket.getPort()));
				Thread r = new Thread(new ReceiveThread(clientSocket, connectMessage.getName()));
				Thread s = new Thread(new SendThread(clientSocket, connectMessage.getName()));

				connectMessage = new ConnectMessage("OK", MessageStatus.REPLY);

				try {
					output.write(Message.serializeMessage(connectMessage));
					output.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}

				r.start();
				s.start();
				gotJoinMessage = false;
			}

			// Annars skicka bara tillbaka ett meddelande att "id är upptaget" / får inte ansluta
			else
			{
				connectMessage = new ConnectMessage("NOT-OK", MessageStatus.REPLY);

				try {
					output.write(Message.serializeMessage(connectMessage));
					output.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}

				gotJoinMessage = false;
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