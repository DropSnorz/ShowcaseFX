package com.dropsnorz.showcasefx.example;

import com.dropsnorz.showcasefx.Showcase;
import com.dropsnorz.showcasefx.ShowcaseBackgroundShape;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class InteractiveShowcaseExample extends Application {
	
	@Override
	public void start(Stage primaryStage) {

		BorderPane root = new BorderPane();
		
		ToolBar toolbar = new ToolBar();
		toolbar.getItems().addAll(new Separator(), new Button("Hi there"));
		root.setTop(toolbar);
		
		StackPane centerPane = new StackPane();
		root.setCenter(centerPane);
		
		Pane contentPane = new HBox();
		centerPane.getChildren().add(contentPane);
		
		Button btn = new Button();
		Button btn2 = new Button("Hi there !");

		btn.setText("Say 'Hello World'");
		btn.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.out.println("Hello World!");
			}
		});
		
		
		
		contentPane.getChildren().add(btn);
		contentPane.getChildren().add(btn2);
		

		Scene scene = new Scene(root, 300, 250);
		
		primaryStage.setTitle("Hello World!");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		Showcase showcase = new Showcase(centerPane);
		showcase.createStep(btn, new Label("Hello World"));
		showcase.createStep(btn2, new Label("Hello World")).setLayer(ShowcaseBackgroundShape.RECTANGLE_FLAT);

		showcase.start();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
