package com.dropsnorz.showcasefx.layouts;

import com.dropsnorz.showcasefx.utils.BoundsUtils;

import javafx.geometry.Bounds;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;

public class AutoShowcaseLayout extends ShowcaseLayout {
	
	protected GridPane mainPane;
	
	public AutoShowcaseLayout() {
		
		this.mainPane = new GridPane();
		
		this.mainPane.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
		this.mainPane.setPadding(new Insets(10,10,10,10));
		this.mainPane.setMouseTransparent(true);
		
	}

	@Override
	public void addContentNode(Node content, Bounds targetBoundsInParent, double parentWidth, double parentHeight) {
		
		double midX = parentWidth / 2;
		double midY = parentHeight / 2;
		int rowIndex = 0;
		int columnIndex = 0;
		Point2D targetCenter = BoundsUtils.getCenter(targetBoundsInParent);
		
		ColumnConstraints col0 = new ColumnConstraints();
		ColumnConstraints col1 = new ColumnConstraints();
		RowConstraints row0 = new RowConstraints();
		RowConstraints row1 = new RowConstraints();

		if (targetCenter.getY() > midY) {
			// target is in lower half of screen
			rowIndex = 0;
			
			row1.setPrefHeight(targetBoundsInParent.getHeight() + 2 * 20);
			row0.setVgrow(Priority.ALWAYS);


		} else {
			// target is in upper half of screen
			rowIndex = 1;
			row0.setPrefHeight(targetBoundsInParent.getHeight() + 2 *  20);
			row1.setVgrow(Priority.ALWAYS);
		}
		if (targetCenter.getX() > midX) {
			// target is in right half of screen
			columnIndex = 0;
			col1.setPrefWidth(targetBoundsInParent.getWidth() + 2 * 20);
			col0.setHgrow(Priority.ALWAYS);

		} else {
			// target is in left half of screen
			columnIndex = 1;
			col0.setPrefWidth(targetBoundsInParent.getWidth() + 2 * 20);
			col1.setHgrow(Priority.ALWAYS);
		}
		
		GridPane.setConstraints(content, columnIndex, rowIndex, 1, 1, HPos.CENTER,VPos.CENTER);
		
		
		this.mainPane.getColumnConstraints().clear();
		this.mainPane.getColumnConstraints().addAll(col0, col1);
		this.mainPane.getRowConstraints().clear();
		this.mainPane.getRowConstraints().addAll(row0, row1);
		
		
		this.mainPane.getChildren().clear();
		this.mainPane.getChildren().add(content);

		
	}

	@Override
	public Node getNode() {
		// TODO Auto-generated method stub
		return mainPane;
	}

}
