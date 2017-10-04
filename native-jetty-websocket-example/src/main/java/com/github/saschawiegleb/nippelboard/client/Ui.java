package com.github.saschawiegleb.nippelboard.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Ui extends Application {
	static int globalWidth = 1024;

	VBox box = new VBox();

	public static Ui instance = null;

	Stage primaryStage = null;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage stage) {
		instance = this;
		stage.setTitle("Nippelboard!");
		Helper.send("all");

		ScrollPane sp = new ScrollPane();
		sp.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);

		StackPane root = new StackPane();
		sp.setContent(box);
		root.getChildren().add(sp);

		stage.setScene(new Scene(root, globalWidth, globalWidth / 16.0 * 9.0));
		stage.show();

		primaryStage = stage;
	}

	static void clear() {
		instance.box.getChildren().clear();
	}

	static double getWidth() {
		if (instance.primaryStage == null || instance.primaryStage.getWidth() == Double.NaN) {
			return globalWidth;
		}
		return instance.primaryStage.getWidth();
	}

}
