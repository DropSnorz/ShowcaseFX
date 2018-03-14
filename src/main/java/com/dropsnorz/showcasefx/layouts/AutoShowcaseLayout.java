package com.dropsnorz.showcasefx.layouts;

import com.dropsnorz.showcasefx.utils.BoundsUtils;

import javafx.geometry.Bounds;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class AutoShowcaseLayout extends ShowcaseLayout {

	protected GridPane mainPane;
	
	private static final String CONTENT_STYLE_CLASS = "showcase-step-content";

	public AutoShowcaseLayout() {

		this.mainPane = new GridPane();
		this.mainPane.setPadding(new Insets(10,10,10,10));

	}

	@Override
	public void addContentNode(Node content, Bounds targetBoundsInParent, double parentWidth, double parentHeight) {

		
		content.getStyleClass().add(CONTENT_STYLE_CLASS);
		
		double midX = parentWidth / 2;
		double midY = parentHeight / 2;
		int rowIndex = 0;
		int columnIndex = 0;

		int rowSpan = 1;
		int colSpan = 1;

		HPos HAlignement = HPos.CENTER;
		VPos VAlignment = VPos.CENTER;

		Point2D targetCenter = BoundsUtils.getCenter(targetBoundsInParent);

		ColumnConstraints col0 = new ColumnConstraints();
		ColumnConstraints col1 = new ColumnConstraints();

		RowConstraints row0 = new RowConstraints();
		RowConstraints row1 = new RowConstraints();


		if (targetCenter.getY() > midY) {
			// target is in lower half of screen
			rowIndex = 0;
			VAlignment = VPos.BOTTOM;
			row1.setPrefHeight(parentHeight - targetBoundsInParent.getMinY()  + 20);
			row0.setVgrow(Priority.ALWAYS);


		} else {
			// target is in upper half of screen
			rowIndex = 1;
			VAlignment = VPos.TOP;
			row0.setPrefHeight(targetBoundsInParent.getMaxY() + 20);
			row1.setVgrow(Priority.ALWAYS);
		}

		if (targetCenter.getX() > midX) {
			// target is in right half of screen
			columnIndex = 0;
			HAlignement = HPos.RIGHT;
			col1.setPrefWidth(parentWidth - targetBoundsInParent.getMinX() + 20);
			col0.setHgrow(Priority.ALWAYS);

		} else {
			// target is in left half of screen
			columnIndex = 1;
			HAlignement = HPos.LEFT;
			col0.setPrefWidth(targetBoundsInParent.getMaxX() + 20);
			col1.setHgrow(Priority.ALWAYS);
		}

		Group contentGroup = new Group(content);
		GridPane.setConstraints(contentGroup, columnIndex, rowIndex, colSpan, rowSpan, HAlignement, VAlignment);

		this.mainPane.setMaxSize(parentWidth, parentHeight);
		this.mainPane.getColumnConstraints().clear();
		this.mainPane.getColumnConstraints().addAll(col0, col1);
		this.mainPane.getRowConstraints().clear();
		this.mainPane.getRowConstraints().addAll(row0, row1);

		this.mainPane.getChildren().clear();
		this.mainPane.getChildren().add(contentGroup);

	}
	
	/**
	 * For debug purpose only, displays layout inner grid 
	 * @param debug
	 */
	public void setDebug(boolean debug) {
		
		if(debug) {
			//Weird thing, sometimes GridLines must be disabled before to show up
			this.mainPane.setGridLinesVisible(false);
			this.mainPane.setGridLinesVisible(true);
		}
		else {
			this.mainPane.setGridLinesVisible(false);
		}
	}


	@Override
	public Node getNode() {
		// TODO Auto-generated method stub
		return mainPane;
	}

}
