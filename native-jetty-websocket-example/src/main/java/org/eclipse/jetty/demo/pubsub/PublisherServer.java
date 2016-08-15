package org.eclipse.jetty.demo.pubsub;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class PublisherServer {

    public static void main(String[] args) throws Exception {
        String host = "localhost";
        int port = 8080;
        System.out.println("args.length: " + args.length);
        if (args.length >= 1) {
            System.out.println(args[0]);
            host = args[0].split(":")[0];
            port = Integer.parseInt(args[0].split(":")[1]);
            System.out.println(String.format("Using host: %s, port: %d", host, port));
        }
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setHost(host);
        connector.setPort(port);
        server.addConnector(connector);

        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.setContextPath("/");
        servletContextHandler.addServlet(PublisherServlet.class, "/pubsub");
        server.setHandler(servletContextHandler);

        server.start();
        server.join();
    }
}
