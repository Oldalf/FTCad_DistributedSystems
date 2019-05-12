package State;

import java.net.InetAddress;
import java.net.Socket;
import java.util.LinkedList;
import java.util.UUID;

import DCAD.GObject;
import Role.ClientRole;
import Role.FrontendRole;

public class clientState {
	//Client related information
	private static clientState single_instance = null;
	public UUID clientId;
	public Socket clientSocket = null;
	public InetAddress clientAddress = null;
	public int clientPort = 0;
	
	//Server related infromation
	public InetAddress serverAddress = null;
	public int serverPort = 0;
	
	//GObject list 
	public LinkedList<GObject> globalObjectList = new LinkedList<GObject>();
	public LinkedList<GObject> myObjects = new LinkedList<GObject>();
	
	public ClientRole role = ClientRole.getInstance();
	
	public static clientState getInstance() {
		if (single_instance == null)
			single_instance = new clientState();
		return single_instance;
	}
	
}
