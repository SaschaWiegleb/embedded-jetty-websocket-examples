package org.eclipse.jetty.demo.pubsub;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class PublisherServer {

    public static void main(String[] args) throws Exception {
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8080);
        server.addConnector(connector);

        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.setContextPath("/");
        servletContextHandler.addServlet(PublisherServlet.class, "/pubsub");
        server.setHandler(servletContextHandler);

        server.start();
        server.join();
    }
}
