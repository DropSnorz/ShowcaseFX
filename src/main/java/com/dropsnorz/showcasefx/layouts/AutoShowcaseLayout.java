package com.dropsnorz.showcasefx.layouts;

import com.dropsnorz.showcasefx.utils.BoundsUtils;

import javafx.geometry.Bounds;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;

public class AutoShowcaseLayout extends ShowcaseLayout {
	
	protected GridPane mainPane;
	
	private int gridColOffset = 0;
	private int gridRowOffset = 0;
	private int gridColSpan = 1;
	private int gridRowSpan = 1;
	
	
	public AutoShowcaseLayout() {
		
		this.mainPane = new GridPane();
				
		this.mainPane.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
		this.mainPane.setPadding(new Insets(10,10,10,10));
						
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
		ColumnConstraints col2 = new ColumnConstraints();

		RowConstraints row0 = new RowConstraints();
		RowConstraints row1 = new RowConstraints();
		RowConstraints row2 = new RowConstraints();

		if (targetCenter.getY() > midY) {
			// target is in lower half of screen
			rowIndex = 1;
			
			row2.setPrefHeight(targetBoundsInParent.getHeight()  + 20);
			row0.setVgrow(Priority.ALWAYS);
			
			
		} else {
			// target is in upper half of screen
			rowIndex = 1;
			row0.setPrefHeight(targetBoundsInParent.getMaxY() + 20);
			row2.setVgrow(Priority.ALWAYS);
		}
		if (targetCenter.getX() > midX) {
			// target is in right half of screen
			columnIndex = 1;
			col2.setPrefWidth(targetBoundsInParent.getWidth() + 20);
			col0.setHgrow(Priority.ALWAYS);
						

		} else {
			// target is in left half of screen
			columnIndex = 1;
			col0.setPrefWidth(targetBoundsInParent.getMaxX() + 20);
			col2.setHgrow(Priority.ALWAYS);
		}
		
		if(columnIndex + gridColOffset >= 0) {
			columnIndex = columnIndex + gridColOffset;
		}
		
		if(rowIndex + gridRowOffset >= 0) {
			rowIndex = rowIndex + gridRowOffset;
		}
		
		
		GridPane.setConstraints(content, columnIndex, rowIndex, this.gridColSpan, this.gridRowSpan, HPos.CENTER, VPos.CENTER);
		
		this.mainPane.getColumnConstraints().clear();
		this.mainPane.getColumnConstraints().addAll(col0, col1, col2);
		this.mainPane.getRowConstraints().clear();
		this.mainPane.getRowConstraints().addAll(row0, row1, row2);
		
		
		this.mainPane.getChildren().clear();
		this.mainPane.getChildren().add(content);

		//Debug
		//this.mainPane.setGridLinesVisible(false);
		//this.mainPane.setGridLinesVisible(true);
		
	}
	
	public void setColumnOffset(int offset) {
		this.gridColOffset = offset;
	}
	
	public void setColumnSpan(int offset) {
		this.gridColSpan = offset;
	}
	
	public void setRowOffset(int offset) {
		this.gridRowOffset = offset;
	}
	
	public void setRowSpan(int offset) {
		this.gridRowSpan = offset;
	}
	
	
	@Override
	public Node getNode() {
		// TODO Auto-generated method stub
		return mainPane;
	}

}
