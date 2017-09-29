package com.github.saschawiegleb.nippelboard.client;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import com.github.saschawiegleb.nippelboard.Conf;

import javafx.scene.layout.HBox;

@WebSocket
public class MyWebSocket {

	private final CountDownLatch closeLatch = new CountDownLatch(1);

	@OnWebSocketMessage
	public void onMessage(String message) {
		Ui.instance.box.getChildren().clear();
		List<String> asList = Arrays.asList(message.split(","));
		int sum = 0;
		HBox hbox = null;
		for (String item : asList) {
			if (sum == 0) {
				hbox = new HBox();
				Ui.instance.box.getChildren().add(hbox);
			}

			hbox.getChildren().add(ImageButton.provideButton(item.split("\\.")[0], Conf.WIDTH));
			sum += Conf.WIDTH;
			if (sum >= Ui.getWidth() - Conf.WIDTH) {
				sum = 0;
			}
		}
	}

	public boolean awaitClose(int duration, TimeUnit unit) throws InterruptedException {
		return this.closeLatch.await(duration, unit);
	}
}