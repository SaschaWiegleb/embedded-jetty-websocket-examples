package org.eclipse.jetty.demo.pubsub;


import org.eclipse.jetty.websocket.WebSocketClient;
import org.eclipse.jetty.websocket.WebSocketClientFactory;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class SubscriberClient {
    public static void main(String[] args) throws Exception {
        String defaultPublisher = "localhost:8080";
        String[] urlsToConnect = new String[] {getUrl(defaultPublisher)};
        if (args.length >= 1) {
            urlsToConnect = getUrlsFromConf(args[0]);
        }
        for (String url : urlsToConnect) {
            connectTo(url);
        }
    }

    private static void connectTo(String url) throws Exception {
        WebSocketClientFactory webSocketClientFactory = new WebSocketClientFactory();
        webSocketClientFactory.start();
        URI uri = URI.create(url);
        WebSocketClient webSocketClient = webSocketClientFactory.newWebSocketClient();
        webSocketClient.open(uri, new SubscriberSocket(url));
    }

    private static String[] getUrlsFromConf(String confFilePath) throws IOException {
        List<String> addresses = Files.readAllLines(new File(confFilePath).toPath());
        List<String> urls = new ArrayList<>();
        for (String address : addresses) {
            if (!address.trim().isEmpty()) {
                urls.add(getUrl(address));
            }
        }
        return urls.toArray(new String[]{});
    }

    private static String getUrl(String address) {
        return "ws://" + address + "/pubsub";
    }
}
