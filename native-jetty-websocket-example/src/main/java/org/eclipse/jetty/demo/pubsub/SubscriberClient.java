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
    private static WebSocketClientFactory webSocketClientFactory;

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("Usage: SubscriberClient <server_ips_file> <plain|secure>");
            System.exit(-1);
        }
        boolean isPlain = "plain".equals(args[1]);
        String[] urlsToConnect = getUrlsFromConf(args[0], isPlain);

        for (String url : urlsToConnect) {
            connectTo(url);
        }
    }

    private static void connectTo(String url) throws Exception {
        if (webSocketClientFactory == null) {
            webSocketClientFactory = new WebSocketClientFactory();
            webSocketClientFactory.start();
        }
        URI uri = URI.create(url);
        WebSocketClient webSocketClient = webSocketClientFactory.newWebSocketClient();
        webSocketClient.open(uri, new SubscriberSocket(url));
    }

    private static String[] getUrlsFromConf(String confFilePath, boolean isPlain) throws IOException {
        List<String> addresses = Files.readAllLines(new File(confFilePath).toPath());
        List<String> urls = new ArrayList<>();
        for (String address : addresses) {
            if (!address.trim().isEmpty()) {
                urls.add(getUrl(address, isPlain));
            }
        }
        return urls.toArray(new String[]{});
    }

    private static String getUrl(String address, boolean isPlain) {
        String scheme = isPlain ? "ws" : "wss";
        return scheme + "://" + address + "/pubsub";
    }
}
