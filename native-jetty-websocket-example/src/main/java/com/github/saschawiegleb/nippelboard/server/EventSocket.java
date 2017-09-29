package com.github.saschawiegleb.nippelboard.server;

import java.io.IOException;

import org.eclipse.jetty.websocket.api.WebSocketAdapter;

public class EventSocket extends WebSocketAdapter {

	@Override
	public void onWebSocketText(String message) {
		switch (message) {
		case "all":
			sendBack(SoundHelper.possibleNamesAsCsv());
			break;
		default:
			super.onWebSocketText(message);
			SoundHelper.playSound(SoundHelper.getSpecificSoundfile(message));
		}
	}

	private void sendBack(String message) {
		if (isConnected()) {
			try {
				getRemote().sendString(message);
			} catch (IOException e) {
				e.printStackTrace(System.err);
			}
		}
	}
}
