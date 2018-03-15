package com.dropsnorz.showcasefx.example;

import com.dropsnorz.showcasefx.Showcase;
import com.dropsnorz.showcasefx.events.ShowcaseEvent;
import com.dropsnorz.showcasefx.layers.ShowcaseLayerShape;
import com.dropsnorz.showcasefx.views.SimpleStepView;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
		Button button = new Button();
		Button btn2 = new Button("Hi there !");

		button.setText("Say 'Hello World'");
		button.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.out.println("Hello World!");
			}
		});

		StackPane root = new StackPane();
		
		Pane mainPane = new FlowPane();
		root.getChildren().add(mainPane);
		mainPane.getChildren().add(button);
		mainPane.getChildren().add(btn2);
		

		Scene scene = new Scene(root, 300, 250);
		
		primaryStage.setTitle("Hello World!");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		Label label = new Label();
		TextField textField = new TextField();
		
		Showcase showcase = new Showcase(root);
		
		showcase.createStep(button, "This is a nice button.");
		showcase.createStep(label, "A small and cute label.");
		showcase.createStep(textField, "Oh, look at this awesome text field !");

		showcase.start();

		showcase.setOnShowcaseStopped(new EventHandler<ShowcaseEvent>() {

			public void handle(ShowcaseEvent event) {
				System.out.println(event.getEventType().getName());
				
			}
			
		});
		showcase.start();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}