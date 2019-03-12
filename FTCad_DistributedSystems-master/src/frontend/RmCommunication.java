package frontend;

// Classen som ska kommunicera mellan Server och Replica Managerserna!
class RmCommunication implements Runnable
{

	public RmCommunication()
	{

	}

	public void run() 
	{
		while(true)
		{
			listenForRequest();
		}
	}

	private void listenForRequest()
	{
		
	}
}