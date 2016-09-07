package org.eclipse.jetty.demo.pubsub;

import org.eclipse.jetty.websocket.WebSocket;

public class SubscriberSocket implements WebSocket.OnTextMessage {
    private String url;

    public SubscriberSocket(String url) {
        this.url = url;
    }

    @Override
    public void onMessage(String message) {
        printMessage("Received message: " + message);
    }

    @Override
    public void onOpen(Connection connection) {
        printMessage("Received connect: " + connection);
    }

    @Override
    public void onClose(int statusCode, String reason) {
        printMessage("Received close: status: " + statusCode + ", reason: " + reason);
    }

    private void printMessage(String message) {
        System.out.println("From " + url + ": " + message);
    }
}
