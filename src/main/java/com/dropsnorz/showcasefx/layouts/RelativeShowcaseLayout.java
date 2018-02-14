package com.dropsnorz.showcasefx.layouts;

import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class RelativeShowcaseLayout extends ShowcaseLayout {
	
	protected FlowPane mainPane;
	
	public RelativeShowcaseLayout(Pos pos) {
		
		mainPane = new FlowPane();
		mainPane.setAlignment(pos);

	}
	
	public RelativeShowcaseLayout(Pos pos, Insets padding) {
		
		mainPane = new FlowPane();
		mainPane.setAlignment(pos);
		mainPane.setPadding(padding);
		
		
	}

	@Override
	public void addContentNode(Node content, Bounds targetBoundsInParent, double parentWidth, double parentHeight) {
		
		mainPane.getChildren().clear();
		mainPane.getChildren().add(content);
	}

	@Override
	public Node getNode() {
		
		return mainPane;
	}

}
