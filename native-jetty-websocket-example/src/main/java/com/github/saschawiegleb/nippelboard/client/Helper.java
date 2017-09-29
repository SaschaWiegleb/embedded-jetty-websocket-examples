package com.github.saschawiegleb.nippelboard.client;

import java.net.URI;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import com.github.saschawiegleb.nippelboard.Conf;

import javaslang.control.Try;

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

				new ParallelTasks<Void>() {
					@Override
					public Try<Void> runTask() {
						// wait for response
						try {
							mySocket.awaitClose(5, TimeUnit.SECONDS);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return null;
					}
				}.start();

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
