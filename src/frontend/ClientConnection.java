package frontend;

import java.net.InetAddress;
import java.util.UUID;

public class ClientConnection 
{

	private UUID ID;
	private InetAddress address = null;
	private int port = 0;

	public ClientConnection()
	{

	}

	public ClientConnection(UUID id, InetAddress address, int port)
	{
		ID = id;
		this.address = address;
		this.port = port;
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
