package frontend;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class Frontend {

	private ServerSocket serverSocket = null;
	private Socket clientSocket = null;

	private InputStream input;
	private OutputStream output;
	private ConcurrentHashMap<UUID, ClientConnection> connectedClients = new ConcurrentHashMap<UUID, ClientConnection>();


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

		// For adding all the linked quseues to the Queue Hashmap
		for(int i = 0; i < 3; i++)
		{
			// Lägg till all blockingqueues till Hashmappen (queues)!
		}

		Thread backendThread = new Thread(new Backend());
		backendThread.start();
	}

	private void running()
	{
		while(true)
		{
			receiveMessage();
			
			// Kolla type
			// Om typ == JoinMessage-Request
			//		Kolla om UUID:et finns i connectedClients om inte 
			//			skicka tillbaka en joinMessage-Reply som är OK-TO-CONNECT eller nått
			
			// Detta ska göras om det är sant att clienten får connecta
			connectedClients.put(clientUUID, new ClientConnection(message.clientUUID, message.clientAddress, message.clientPort));
			Thread r = new Thread(new ReceiveThread(clientSocket, message.clientUUID));
			Thread s = new Thread(new SendThread(clientSocket, message.clientUUID));
			r.start();
			s.start();
			
			// annars skicka bara tillbaka ett meddelande att id är upptaget
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
	}
}