package com.github.saschawiegleb.nippelboard.server;

import java.io.IOException;

import org.eclipse.jetty.websocket.api.WebSocketAdapter;

public class EventSocket extends WebSocketAdapter {

	@Override
	public void onWebSocketText(String message) {
		System.out.println("recieve message: " + message);
		switch (message) {
		case "all":
			sendBack(SoundHelper.possibleNamesAsCsv());
			break;
		default:
			if (message.startsWith("http")) {
				SoundHelper.downloadSound(message);
				break;
			}
			SoundHelper.playSound(SoundHelper.getSpecificSoundfile(message));
		}
	}

	private void sendBack(String message) {
		if (isConnected()) {
			try {
				System.out.println("send message: " + message);
				getRemote().sendString(message);
			} catch (IOException e) {
				e.printStackTrace(System.err);
			}
		}
	}
}
