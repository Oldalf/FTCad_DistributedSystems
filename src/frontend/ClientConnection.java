package frontend;

import java.net.InetAddress;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

import message.Message;

public class ClientConnection 
{

	private UUID ID;
	private InetAddress address = null;
	private int port = 0;

	// Kön som Output läser ifrån samt att det är denna som alla meddelanden läggs i när de kommer från Replicamanagersen
	private LinkedBlockingQueue<Message> messageQueue = new LinkedBlockingQueue<Message>();

	public ClientConnection()
	{

	}

	public ClientConnection(UUID id, InetAddress address, int port)
	{
		ID = id;
		this.address = address;
		this.port = port;
	}

	public LinkedBlockingQueue<Message> getMessageQueue()
	{
		return messageQueue;
	}

	public UUID getUUID()
	{
		return ID;
	}

	public boolean checkUUID(UUID id)
	{
		if(ID.equals(id))
			return true;

		else
			return false;
	}
}
