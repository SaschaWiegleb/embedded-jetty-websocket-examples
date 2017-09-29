package com.github.saschawiegleb.nippelboard.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;

import com.github.saschawiegleb.nippelboard.Conf;

public class EventServer {
	public static void main(String[] args) {
		Server server = new Server();
		ServerConnector connector = new ServerConnector(server);
		connector.setPort(Conf.SERVER_PORT);
		server.addConnector(connector);

		// Setup the basic application "context" for this application at "/"
		// This is also known as the handler tree (in jetty speak)
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		// Add a websocket to a specific path spec
		context.addServlet(EventServlet.class, Conf.SERVER_PATH + "*");
		server.setHandler(context);

		try {
			server.start();
			server.dump(System.err);
			server.join();
		} catch (Throwable t) {
			t.printStackTrace(System.err);
		}
	}
}
