package message;

public enum Reply {
		OK, //use to indicate for clients that a connection is established 
		CHANGEUUID, // use to indicate to the clients that the UUID already exists as a established connection in the server
		ERROR, //Server error. 
}