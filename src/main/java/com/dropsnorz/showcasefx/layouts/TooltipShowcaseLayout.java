package com.dropsnorz.showcasefx.layouts;

import com.dropsnorz.showcasefx.utils.BoundsUtils;

import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;

public class TooltipShowcaseLayout extends ShowcaseLayout {

	protected AnchorPane mainPane;
	protected HBox contentPane;

	protected DropShadow dropShadow;

	Polygon triangle = new Polygon();



	public TooltipShowcaseLayout(){
		mainPane = new AnchorPane();
		contentPane = new HBox();

		contentPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		contentPane.setPadding(new Insets(10,10,10,10));
		contentPane.setEffect(dropShadow);
		mainPane.getChildren().add(triangle);
		mainPane.getChildren().add(contentPane);

		dropShadow = new DropShadow();
		dropShadow.setRadius(5.0);
		dropShadow.setOffsetX(3.0);
		dropShadow.setOffsetY(3.0);
		dropShadow.setColor(Color.color(0, 0, 0, 0.5));
		
		triangle.setEffect(dropShadow);
		triangle.setFill(Color.WHITE);		

		contentPane.setEffect(dropShadow);

	}

	@Override
	public void addContentNode(Node content, Bounds targetBoundsInParent, double parentWidth, double parentHeight) {

		contentPane.getChildren().clear();
		contentPane.getChildren().add(content);
		
		int contentOffset = 10;
		int margin = 15;

		Point2D targetCenter = BoundsUtils.getCenter(targetBoundsInParent);

		// target is in lower half of screen


		/*
		 * This should be run after the next JavaFx thread update.
		 * contentPane state in scene graph will be refreshed
		 */
		Platform.runLater(() -> {
			double x = targetCenter.getX() - (this.contentPane.getWidth()/ 2);
			double y = targetCenter.getY() + contentOffset;
			
			if(targetCenter.getX() > parentWidth - this.contentPane.getWidth()) {
				//target is in the right side of the screen
				x = parentWidth -  this.contentPane.getWidth() - margin;
			}
			else if(targetCenter.getX() <  this.contentPane.getWidth()) {
				//target is in the left side of the screen
				x = margin;
			}
			
			
			if(targetCenter.getY() > parentHeight - this.contentPane.getHeight()) {
				//target is in the bottom side of the screen
				y = targetCenter.getY() - this.contentPane.getHeight() - 2*contentOffset;
				
				triangle.getPoints().clear();
				triangle.getPoints().addAll(10.0, 10.0, 0.0, 0.0, 20.0, 0.0);
				
				triangle.relocate(targetCenter.getX() - 10, targetCenter.getY() - 2*contentOffset);
				
				triangle.toFront();
			}
			else {
				triangle.getPoints().clear();
				triangle.getPoints().addAll(10.0, 0.0,  0.0, 10.0,20.0, 10.0);
				triangle.relocate(targetCenter.getX() - 10, targetCenter.getY());
				triangle.toBack();

			}

			System.out.println(contentPane.getWidth());
			System.out.println(contentPane.getHeight());

			contentPane.relocate(x, y);
		});

	}

	@Override
	public Node getNode() {
		// TODO Auto-generated method stub
		return mainPane;
	}
	
	
	public void setTooltipPadding(Insets insets) {
		this.contentPane.setPadding(insets);
	}

}
