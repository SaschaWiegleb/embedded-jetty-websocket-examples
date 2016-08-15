package org.eclipse.jetty.demo.pubsub;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

public class SubscriberSocket extends WebSocketAdapter {
    private String url;

    public SubscriberSocket(String url) {
        this.url = url;
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        printMessage("Received close: status: " + statusCode + ", reason: " + reason);
        super.onWebSocketClose(statusCode, reason);
    }

    private void printMessage(String message) {
        System.out.println("From " + url + ": " + message);
    }

    @Override
    public void onWebSocketConnect(Session sess) {
        printMessage("Received connect: " + sess);
        super.onWebSocketConnect(sess);
    }

    @Override
    public void onWebSocketError(Throwable cause) {
        printMessage("Received error: " + cause);
        super.onWebSocketError(cause);
    }

    @Override
    public void onWebSocketText(String message) {
        printMessage("Received message: " + message);
        super.onWebSocketText(message);
    }
}
