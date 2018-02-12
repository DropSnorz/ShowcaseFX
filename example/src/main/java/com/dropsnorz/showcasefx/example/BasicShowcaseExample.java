package com.dropsnorz.showcasefx.example;

import com.dropsnorz.showcasefx.Showcase;
import com.dropsnorz.showcasefx.ShowcaseBackgroundShape;
import com.dropsnorz.showcasefx.events.ShowcaseEvent;
import com.dropsnorz.showcasefx.views.SimpleStepView;

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
		
		showcase.createStep(btn, new SimpleStepView("Hi !", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat"));
		showcase.createStep(btn2, new SimpleStepView("No Title !", "Hello World")).setLayer(ShowcaseBackgroundShape.RECTANGLE_FLAT);

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