package com.dropsnorz.showcasefx.layouts;

import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;


public class AlignmentShowcaseLayout extends ShowcaseLayout {
	
	protected FlowPane mainPane;
	
	public AlignmentShowcaseLayout(Pos pos) {
		
		mainPane = new FlowPane();
		mainPane.setAlignment(pos);

	}
	
	public AlignmentShowcaseLayout(Pos pos, Insets padding) {
		
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
