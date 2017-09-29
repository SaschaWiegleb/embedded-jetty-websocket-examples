package com.github.saschawiegleb.nippelboard.client;

import java.io.File;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

class ImageButton extends Button {
	private ImageView PRESSED = new ImageView(new Image(new File("img/pressed.jpg").toURI().toString()));
	private ImageView RELEASED = new ImageView(new Image(new File("img/released.jpg").toURI().toString()));

	private final String STYLE_NORMAL = "-fx-background-color: transparent;";
	private final String STYLE_PRESSED = "-fx-background-color: transparent;";

	private ImageButton(final String name, int width) {
		PRESSED.setFitWidth(width);
		PRESSED.setPreserveRatio(true);
		RELEASED.setFitWidth(width);
		RELEASED.setPreserveRatio(true);

		setGraphic(RELEASED);
		setStyle(STYLE_NORMAL);

		setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				setGraphic(PRESSED);
				setStyle(STYLE_PRESSED);
				Helper.send(name);
			}
		});

		setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				setGraphic(RELEASED);
				setStyle(STYLE_NORMAL);
			}
		});
	}

	public static VBox provideButton(String itemName, int width) {
		VBox box = new VBox();
		box.getChildren().add(new ImageButton(itemName, width));

		StackPane root = new StackPane();
		int file = (int) (Math.random() * ((16) + 1));
		ImageView iv2 = new ImageView(new Image(new File("img/tape/" + file + ".jpg").toURI().toString()));
		iv2.setFitWidth(width);
		iv2.setPreserveRatio(true);
		root.getChildren().add(iv2);

		Text t = new Text();
		t.setText(itemName);
		t.setFont(Font.font("Verdana", 20));
		root.getChildren().add(t);

		box.getChildren().add(root);
		box.setAlignment(Pos.CENTER);

		return box;
	}
}
