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
				message.executeForFrontend(FrontendState.getInstance());
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}