package org.eclipse.jetty.demo.pubsub;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import java.net.URI;
import java.util.concurrent.Future;

public class SubscriberClient {
    public static void main(String[] args) throws Exception {
        URI uri = URI.create("ws://localhost:8080/pubsub");
        WebSocketClient webSocketClient = new WebSocketClient();
        webSocketClient.start();
        SubscriberSocket subscriberSocket = new SubscriberSocket();
        Future<Session> sessionFuture = webSocketClient.connect(subscriberSocket, uri);
        Session webSocketSession = sessionFuture.get();
    }
}
