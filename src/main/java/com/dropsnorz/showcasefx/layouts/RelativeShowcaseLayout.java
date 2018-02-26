package com.dropsnorz.showcasefx.layouts;

import com.dropsnorz.showcasefx.utils.BoundsUtils;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class RelativeShowcaseLayout extends ShowcaseLayout{

	protected Pane mainPane;

	protected double offsetX = 0;
	protected double offsetY = 0;

	protected boolean computeFromTargetCenter = false;


	public RelativeShowcaseLayout() {
		this.mainPane = new Pane();

	}

	public RelativeShowcaseLayout( double offsetX, double offsetY) {
		this();
		this.offsetX = offsetX;
		this.offsetY = offsetY;

	}

	public RelativeShowcaseLayout(double offsetX, double offsetY, boolean computeFromTargetCenter) {
		this(offsetX, offsetY);
		this.computeFromTargetCenter = computeFromTargetCenter;
	}



	@Override
	public void addContentNode(Node content, Bounds targetBoundsInParent, double parentWidth, double parentHeight) {

		mainPane.getChildren().clear();
		mainPane.getChildren().add(content);

		double x = 0;
		double y = 0 ;

		if(computeFromTargetCenter) {
			Point2D targetCenter = BoundsUtils.getCenter(targetBoundsInParent);
			x = targetCenter.getX() + offsetX;
			y = targetCenter.getY() + offsetY;
		} else {
			x = targetBoundsInParent.getMinX() + offsetX;
			y = targetBoundsInParent.getMinY() + offsetY;
		}
		content.relocate(x, y);

	}

	@Override
	public Node getNode() {
		// TODO Auto-generated method stub
		return mainPane;
	}

	public double getOffsetX() {
		return offsetX;
	}

	public void setOffsetX(double offsetX) {
		this.offsetX = offsetX;
	}

	public double getOffsetY() {
		return offsetY;
	}

	public void setOffsetY(double offsetY) {
		this.offsetY = offsetY;
	}






}
