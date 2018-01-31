package com.dropsnorz.showcasefx.views;


import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class SimpleStepView extends Pane {
	
	protected Label titleLabel;
	protected Label contentLabel;
	
	
	public SimpleStepView(String title, String content) {
		
		
		VBox root = new VBox();
		
		titleLabel = new Label(title);
		titleLabel.setTextFill(Color.WHITE);
		titleLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 21));

		contentLabel = new Label(content);
		contentLabel.setTextFill(Color.WHITE);
		contentLabel.setWrapText(true);

				
		root.getChildren().add(titleLabel);
		root.getChildren().add(contentLabel);
		
		this.getChildren().add(root);
		
		
	}
	

}
