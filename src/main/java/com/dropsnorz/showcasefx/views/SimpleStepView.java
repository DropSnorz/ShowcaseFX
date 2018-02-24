package com.dropsnorz.showcasefx.views;


import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class SimpleStepView extends VBox {
	
	protected Label titleLabel;
	protected Label contentLabel;
	
	
	public SimpleStepView(String title, String content) {
		
		this.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
				
		titleLabel = new Label(title);
		titleLabel.setTextFill(Color.BLACK);
		titleLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 21));

		contentLabel = new Label(content);
		contentLabel.setTextFill(Color.BLACK);
		contentLabel.setWrapText(true);

				
		this.getChildren().add(titleLabel);
		this.getChildren().add(contentLabel);
		
		
		
	}
	

}
