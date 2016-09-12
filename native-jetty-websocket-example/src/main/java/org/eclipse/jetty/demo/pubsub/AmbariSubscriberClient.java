package org.eclipse.jetty.demo.pubsub;

import org.eclipse.jetty.websocket.WebSocketClient;
import org.eclipse.jetty.websocket.WebSocketClientFactory;

import java.net.URI;

public class AmbariSubscriberClient {
    public static void main(String[] args) throws Exception {
        String serverAndPort = null;
        boolean isPlain = false;
        if (args.length != 2) {
            System.out.println("Usage: AmbariSubscriberClient <ip:port> <plain|secure>");
            System.exit(-1);
        } else {
            serverAndPort = args[0];
            isPlain = "plain".equals(args[1]);
        }
        connectTo(getUrl(serverAndPort, isPlain));
    }

    private static void connectTo(String url) throws Exception {
        WebSocketClientFactory webSocketClientFactory = new WebSocketClientFactory();
        webSocketClientFactory.start();
        URI uri = URI.create(url);
        WebSocketClient webSocketClient = webSocketClientFactory.newWebSocketClient();
        webSocketClient.open(uri, new SubscriberSocket(url));
    }

    private static String getUrl(String address, boolean isPlain) {
        String scheme = isPlain ? "ws" : "wss";
        return scheme + "://" + address + "/alerts";
    }

}
