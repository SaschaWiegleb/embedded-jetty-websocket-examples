package com.github.saschawiegleb.nippelboard;

public class Conf {
	public static String soundFolder = "sound/";

	public static int WIDTH = 200;

	public static int SERVER_PORT = 8080;

	public static String SERVER_PATH = "/events/";

	public static String SERVER_IP = "localhost";

	public static String SERVER_ADRESS = "ws://" + SERVER_IP + ":" + SERVER_PORT + SERVER_PATH;
}
