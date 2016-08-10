package org.eclipse.jetty.demo.pubsub;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

import java.io.IOException;
import java.util.Random;

public class PublisherSocket extends WebSocketAdapter {
    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        System.out.println("Received close: status: " + statusCode + ", reason: " + reason);
        super.onWebSocketClose(statusCode, reason);
    }

    @Override
    public void onWebSocketConnect(Session sess) {
        System.out.println("Received connect");
        for (int i = 0; i < 10; i++) {
            try {
                String eventString = "Server health: " + new Random().nextBoolean();
                System.out.println("Sending event: " + i + ", event string: " + eventString);
                sess.getRemote().sendString(eventString);
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onWebSocketError(Throwable cause) {
        System.out.println("Received error: " + cause);
        super.onWebSocketError(cause);
    }
}
