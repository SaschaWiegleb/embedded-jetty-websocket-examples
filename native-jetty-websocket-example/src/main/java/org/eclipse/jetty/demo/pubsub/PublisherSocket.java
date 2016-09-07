package org.eclipse.jetty.demo.pubsub;

import org.eclipse.jetty.websocket.WebSocket;

import java.io.IOException;
import java.util.Random;

public class PublisherSocket implements WebSocket.OnTextMessage {

    @Override
    public void onMessage(String s) {
        System.out.println("Received message on server: " + s);
    }

    @Override
    public void onOpen(Connection connection) {
        System.out.println("Received connect");
        for (int i = 0; i < PublisherServer.getNumberOfRuns(); i++) {
            try {
                String eventString = "Server health: " + new Random().nextBoolean();
                System.out.println("Sending event: " + i + ", event string: " + eventString);
                connection.sendMessage(eventString);
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClose(int statusCode, String reason) {
        System.out.println("Received close: status: " + statusCode + ", reason: " + reason);
    }
}
