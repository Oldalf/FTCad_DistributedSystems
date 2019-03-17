package frontend;

import java.util.concurrent.LinkedBlockingQueue;

import State.FrontendState;
import message.Message;

public class MessageHandlerThread implements Runnable
{
	private LinkedBlockingQueue<Message> messageQueue;
	private volatile boolean isAlive = false;
	
	public MessageHandlerThread(LinkedBlockingQueue<Message> fromRMCommunication){
		isAlive = true;
		this.messageQueue = fromRMCommunication;
	}
	public void run()
	{
		while(isAlive) {
			try {
				Message message = this.messageQueue.take();
				if(FrontendState.connectedClients.containsKey(message.getReceiverUUID())) {
					FrontendState.connectedClients.get(message.getSenderUUID()).getMessageQueue().put(message);
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
