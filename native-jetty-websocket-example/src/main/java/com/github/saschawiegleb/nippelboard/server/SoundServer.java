package com.github.saschawiegleb.nippelboard.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;

import com.github.saschawiegleb.nippelboard.Conf;

/**
 * java -jar server.jar 8888 sound
 * 
 * @author swiegleb
 *
 */
public class SoundServer {
	public static void main(String[] args) {
		System.out.println("Use: java -jar soundServer.jar serverPort soundFolder");
		if (args.length > 0) {
			Conf.SERVER_PORT = Integer.parseInt(args[0]);
		}
		if (args.length > 1) {
			Conf.soundFolder = args[1];
		}

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
