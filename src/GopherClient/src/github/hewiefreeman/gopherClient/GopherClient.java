package github.hewiefreeman.gopherClient;

import javax.websocket.*;
import java.net.URI;

public class GopherClient {
	
	// FROM: https://stackoverflow.com/a/26454417/1584308
	
	Session session = null;
	private MessageHandler msgHandler;
	
	GopherClient(URI endpointURI){
		try {
			WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, endpointURI);
		}catch(Exception e){
        	throw new RuntimeException(e);
        }
	}
	
	// Open callback
	@OnOpen
	public void onOpen(Session userSession) {
		System.out.println("opening websocket");
		session = userSession;
	}
	
	// Close callback
	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		System.out.println("closing websocket");
		session = null;
	}

	// Message callback
	@OnMessage
	public void onMessage(String message) {
		if(msgHandler != null){
			msgHandler.handleMessage(message);
		}
	}

	// Message handler
	public void addMessageHandler(MessageHandler handler) {
		msgHandler = handler;
	}

    // Sending messages
	public void sendMessage(String message) {
		session.getAsyncRemote().sendText(message);
	}

    // Message handler
	public static interface MessageHandler {
		public void handleMessage(String message);
	}
}
