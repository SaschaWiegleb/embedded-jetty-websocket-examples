package org.eclipse.jetty.demo.pubsub;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

public class SubscriberSocket extends WebSocketAdapter {
    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        System.out.println("Received close: status: " + statusCode + ", reason: " + reason);
        super.onWebSocketClose(statusCode, reason);
    }

    @Override
    public void onWebSocketConnect(Session sess) {
        System.out.println("Received connect: " + sess);
        super.onWebSocketConnect(sess);
    }

    @Override
    public void onWebSocketError(Throwable cause) {
        System.out.println("Received error: " + cause);
        super.onWebSocketError(cause);
    }

    @Override
    public void onWebSocketText(String message) {
        System.out.println("Received message: " + message);
        super.onWebSocketText(message);
    }
}
