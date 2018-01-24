package com.dropsnorz.showcasefx.example;

import com.dropsnorz.showcasefx.Showcase;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class BasicShowcaseExample extends Application {

	@Override
	public void start(Stage primaryStage) {
		Button btn = new Button();
		btn.setText("Say 'Hello World'");
		btn.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.out.println("Hello World!");
			}
		});

		StackPane root = new StackPane();
		
		Pane mainPane = new Pane();
		root.getChildren().add(mainPane);
		mainPane.getChildren().add(btn);

		Scene scene = new Scene(root, 300, 250);
		
		primaryStage.setTitle("Hello World!");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		Showcase showcase = new Showcase(root);
		showcase.addStep(btn, new Label("Hello World"));
		
		showcase.start();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}