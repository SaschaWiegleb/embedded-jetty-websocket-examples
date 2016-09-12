package org.eclipse.jetty.demo.pubsub;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.server.ssl.SslSelectChannelConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.resource.FileResource;
import org.eclipse.jetty.util.ssl.SslContextFactory;

public class PublisherServer {

    private static int numberOfRuns = 10;

    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            System.out.println("Usage: java PublisherServer <hostname>:<port> <number_of_runs> <plain|secure>");
            System.exit(-1);
        }

        String host;
        int port;
        System.out.println(args[0]);
        host = args[0].split(":")[0];
        port = Integer.parseInt(args[0].split(":")[1]);
        System.out.println(String.format("Using host: %s, port: %d", host, port));

        numberOfRuns = Integer.parseInt(args[1]);

        boolean isPlain = "plain".equals(args[2]);

        Server server = new Server();
        SelectChannelConnector connector;
        if (isPlain) {
            connector = new SelectChannelConnector();
        } else {
            SslContextFactory sslContextFactory = new SslContextFactory();
            sslContextFactory.setKeyStoreResource(new FileResource(
                    PublisherServer.class.getResource("/websocket_keystore.jks")));
            sslContextFactory.setKeyStorePassword("password");
            sslContextFactory.setKeyManagerPassword("password");
            connector = new SslSelectChannelConnector(sslContextFactory);
        }
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

    public static int getNumberOfRuns() {
        return numberOfRuns;
    }
}
