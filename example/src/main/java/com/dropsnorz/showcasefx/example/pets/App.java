package com.dropsnorz.showcasefx.example.pets;

import java.io.IOException;

import com.dropsnorz.showcasefx.Showcase;
import com.dropsnorz.showcasefx.ShowcaseBackgroundShape;
import com.dropsnorz.showcasefx.events.ShowcaseEvent;
import com.dropsnorz.showcasefx.views.SimpleStepView;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {

	@Override
	public void start(Stage primaryStage) {

		double width = 900;
		double height = 550;


		Parent rootNode = null;
		
		System.out.println(this.getClass().getResource("/MainView.fxml"));

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainView.fxml"));
		try {
			rootNode = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Scene scene = new Scene(rootNode, width, height);
		String css = App.class.getResource("/pets.css").toExternalForm();
		scene.getStylesheets().add(css);
		
		primaryStage.setScene(scene);
		primaryStage.centerOnScreen();
		primaryStage.show();


	}

	public static void main(String[] args) {
		launch(args);
	}

}