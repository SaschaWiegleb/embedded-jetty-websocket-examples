package org.eclipse.jetty.demo.pubsub;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class PublisherServlet extends WebSocketServlet {
    @Override
    public void configure(WebSocketServletFactory webSocketServletFactory) {
        webSocketServletFactory.register(PublisherSocket.class);
    }
}
