package org.eclipse.jetty.demo.pubsub;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.client.WebSocketClient;

public class SubscriberClient {
	public static void main(String[] args) throws Exception {
		String defaultPublisher = "localhost:8080";
		String[] urlsToConnect = new String[] { getUrl(defaultPublisher) };
		if (args.length >= 1) {
			urlsToConnect = getUrlsFromConf(args[0]);
		}
		for (String url : urlsToConnect) {
			connectTo(url);
		}
	}

	private static void connectTo(String url) throws Exception {
		URI uri = URI.create(url);
		WebSocketClient webSocketClient = new WebSocketClient();
		webSocketClient.start();
		SubscriberSocket subscriberSocket = new SubscriberSocket(url);
		Future<Session> sessionFuture = webSocketClient.connect(subscriberSocket, uri);
		Session webSocketSession = sessionFuture.get();
	}

	private static String[] getUrlsFromConf(String confFilePath) throws IOException {
		List<String> addresses = Files.readAllLines(new File(confFilePath).toPath(), Charset.defaultCharset());
		List<String> urls = new ArrayList<>();
		for (String address : addresses) {
			if (!address.trim().isEmpty()) {
				urls.add(getUrl(address));
			}
		}
		return urls.toArray(new String[] {});
	}

	private static String getUrl(String address) {
		return "ws://" + address + "/pubsub";
	}
}
