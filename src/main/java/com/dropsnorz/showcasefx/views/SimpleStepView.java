package com.dropsnorz.showcasefx.views;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SimpleStepView extends VBox {
	
	protected Label titleLabel;
	protected Label contentLabel;
	
	private static final String DEFAULT_STYLE_CLASS = "simple-step-view";

	
	public SimpleStepView(String title, String body) {
		
		
		this.setMaxWidth(300);
		this.getStyleClass().add(DEFAULT_STYLE_CLASS);
								
		titleLabel = new Label(title);
		titleLabel.setTextFill(Color.WHITE);
		titleLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 21));

		contentLabel = new Label(body);
		contentLabel.setTextFill(Color.WHITE);
		contentLabel.setWrapText(true);
				
		this.getChildren().add(titleLabel);
		this.getChildren().add(contentLabel);
		
		
	}
	

}
