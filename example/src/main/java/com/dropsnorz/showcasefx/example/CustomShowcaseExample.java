package com.dropsnorz.showcasefx.example;

import java.io.IOException;

import com.dropsnorz.showcasefx.Showcase;
import com.dropsnorz.showcasefx.example.pets.App;
import com.dropsnorz.showcasefx.layers.ShowcaseLayerShape;
import com.dropsnorz.showcasefx.layouts.ShowcaseLayout;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;


public class CustomShowcaseExample extends Application {

	@FXML
	private TextField textField1;

	@FXML
	private StackPane rootPane;

	@FXML
	private ProgressBar indicator1;

	@FXML
	private Button button1;

	@FXML
	private Label label1;
	
	private Showcase showcase;



	@Override
	public void start(Stage primaryStage) {

		double width = 600;
		double height = 400;

		Parent rootNode = null;


		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Demo.fxml"));
		loader.setController(this);
		try {
			rootNode = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Scene scene = new Scene(rootNode, width, height);
		String css = App.class.getResource("/custom.css").toExternalForm();
		scene.getStylesheets().add(css);
		
		primaryStage.setScene(scene);
		primaryStage.centerOnScreen();
		primaryStage.show();


	}

	public void initialize() {
		
		showcase = new Showcase(rootPane);
		
		showcase.setDefaultLayout(new ShowcaseLayout() {
			Pane pane = new Pane();
			@Override
			public void addContentNode(Node content, Bounds targetBoundsInParent, double parentWidth,
					double parentHeight) {
				pane.getChildren().clear();
				pane.getChildren().add(content);
			}
			
			@Override
			public Node getNode() {
				// TODO Auto-generated method stub
				return pane;
			}
		});
		
		
		showcase.setDefaultLayer(new ShowcaseLayerShape() {

			@Override
			public Shape generate(Bounds targetBounds, double parentWidth, double parentHeight) {
				
				int offset = 10;
				Rectangle rectangle = new Rectangle(targetBounds.getMinX() - offset, 
						targetBounds.getMinY() - offset, 
						targetBounds.getWidth() + 2*offset, 
						targetBounds.getHeight() + 2*offset);
				
				rectangle.setArcHeight(10);
				rectangle.setArcWidth(10);
				
				return rectangle;
			}
			
		});
		
		
		showcase.createStep(button1, "Start the showcase by clicking this button");
		showcase.createStep(indicator1, "This is a JavaFx progress indicator");
		showcase.createStep(textField1, "This is a JavaFx Texfield");
		showcase.createStep(label1, "This is a JavaFx label");
		
		button1.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				showcase.start();
				
			}
		});


	}

	public static void main(String[] args) {
		launch(args);
	}

}