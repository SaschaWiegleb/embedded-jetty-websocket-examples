package com.github.saschawiegleb.nippelboard.client;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import com.github.saschawiegleb.nippelboard.Conf;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;

@WebSocket
public class MyWebSocket {

	private CountDownLatch closeLatch = new CountDownLatch(1);

	@OnWebSocketMessage
	public void onMessage(final String message) {
		System.out.println("recieve message: " + message);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
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

				Button upload = new Button("Upload File");
				upload.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent e) {
						TextInputDialog dialog = new TextInputDialog();
						// dialog.setTitle("Text Input Dialog");
						// dialog.setHeaderText("Look, a Text Input Dialog");
						dialog.setContentText("Please enter url:");

						// Traditional way to get the response value.
						Optional<String> result = dialog.showAndWait();
						if (result.isPresent() && result.get().startsWith("http")) {
							Helper.send(result.get());
						} else {
							System.err.println("Fehler bei der URL");
						}
						Helper.send("all");
					}
				});
				hbox.getChildren().add(upload);
			}
		});
		this.closeLatch = new CountDownLatch(1);
	}

	public boolean awaitClose(int duration, TimeUnit unit) throws InterruptedException {
		this.closeLatch = new CountDownLatch(1);
		return this.closeLatch.await(duration, unit);
	}
}