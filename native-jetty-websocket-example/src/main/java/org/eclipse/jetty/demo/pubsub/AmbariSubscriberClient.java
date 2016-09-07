package org.eclipse.jetty.demo.pubsub;

import org.eclipse.jetty.websocket.WebSocketClient;
import org.eclipse.jetty.websocket.WebSocketClientFactory;

import java.net.URI;

public class AmbariSubscriberClient {
    public static void main(String[] args) throws Exception {
        String defaultPublisher = "c6401.ambari.apache.org:8080";
        if (args.length >= 1) {
            defaultPublisher = args[0];
        }
        connectTo(getUrl(defaultPublisher));
    }

    private static void connectTo(String url) throws Exception {
        WebSocketClientFactory webSocketClientFactory = new WebSocketClientFactory();
        webSocketClientFactory.start();
        URI uri = URI.create(url);
        WebSocketClient webSocketClient = webSocketClientFactory.newWebSocketClient();
        webSocketClient.open(uri, new SubscriberSocket(url));
    }

    private static String getUrl(String address) {
        return "ws://" + address + "/alerts";
    }

}
