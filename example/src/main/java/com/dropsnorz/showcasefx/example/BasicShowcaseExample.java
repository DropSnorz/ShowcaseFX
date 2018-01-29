package com.dropsnorz.showcasefx.example;

import com.dropsnorz.showcasefx.Showcase;
import com.dropsnorz.showcasefx.ShowcaseBackgroundShape;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
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
		Button btn2 = new Button("Hi there !");

		btn.setText("Say 'Hello World'");
		btn.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.out.println("Hello World!");
			}
		});

		StackPane root = new StackPane();
		
		Pane mainPane = new FlowPane();
		root.getChildren().add(mainPane);
		mainPane.getChildren().add(btn);
		mainPane.getChildren().add(btn2);
		

		Scene scene = new Scene(root, 300, 250);
		
		primaryStage.setTitle("Hello World!");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		Showcase showcase = new Showcase(root);
		showcase.addStep(btn, new Label("Hello World"));
		showcase.addStep(btn2, new Label("Hello World"), ShowcaseBackgroundShape.RECTANGLE_FLAT);

		showcase.start();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}