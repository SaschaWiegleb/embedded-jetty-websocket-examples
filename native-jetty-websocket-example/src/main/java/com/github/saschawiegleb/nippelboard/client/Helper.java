package com.github.saschawiegleb.nippelboard.client;

import java.net.URI;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import com.github.saschawiegleb.nippelboard.Conf;

public class Helper {

	static URI uri = URI.create(Conf.SERVER_ADRESS);

	static void send(String message) {
		WebSocketClient client = new WebSocketClient();
		try {
			try {
				client.start();
				// The socket that receives events
				final MyWebSocket mySocket = new MyWebSocket();
				// Attempt Connect
				Future<Session> fut = client.connect(mySocket, uri);

				// Wait for Connect
				Session session = fut.get();
				// Send a message
				session.getRemote().sendString(message);
				System.out.println("send message: " + message);

				if (message.equals("all"))
					mySocket.awaitClose(5, TimeUnit.MILLISECONDS);
				if (message.startsWith("http"))
					mySocket.awaitClose(5, TimeUnit.SECONDS);

				// Close session
				session.close();
			} finally {
				client.stop();
			}
		} catch (Throwable t) {
			t.printStackTrace(System.err);
		}
	}
}
