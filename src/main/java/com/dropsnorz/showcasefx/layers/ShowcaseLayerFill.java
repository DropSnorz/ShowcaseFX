package com.dropsnorz.showcasefx.layers;

import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;

public abstract class ShowcaseLayerFill implements ShowcaseLayer {
	
	public abstract Background generate(double parentWidth, double parentHeight, Bounds nodeBounds);
	
	public Node generateNode(double parentWidth, double parentHeight, Bounds nodeBounds) {
		
		Background background = this.generate(parentWidth, parentHeight, nodeBounds);
		
		Pane pane = new Pane();
		pane.setBackground(background);
		
		return pane;
	}
	
		
	public static ShowcaseLayerFill CIRCLE_GRADIENT = new ShowcaseLayerFill() {

		@Override
		public Background generate(double parentWidth, double parentHeight, Bounds nodeBounds) {
			
			double centerX = nodeBounds.getMinX() + (nodeBounds.getWidth() / 2);
			double centerY = nodeBounds.getMinY() + (nodeBounds.getHeight() / 2);

			double x_relative = centerX / parentWidth;
			double y_relative = centerY / parentHeight;
			
			double focus = Math.max(x_relative, y_relative);
			
			int radiusOffset = 30;
			
			double radius = Math.max((centerX + radiusOffset) / parentWidth , (centerY + radiusOffset) / parentHeight );

			RadialGradient shadePaint = new RadialGradient(
					0,focus, x_relative, y_relative, radius, true, CycleMethod.NO_CYCLE,
					new Stop(1, Color.rgb(0, 0, 0, 0.6)),
					new Stop(0, Color.TRANSPARENT)
					);
			
			return new Background(new BackgroundFill(shadePaint, null, Insets.EMPTY));
		}
		
	};

	public static ShowcaseLayerFill CIRCLE_FLAT = new ShowcaseLayerFill() {

		@Override
		public Background generate(double parentWidth, double parentHeight, Bounds nodeBounds) {
			
			double centerX = nodeBounds.getMinX() + (nodeBounds.getWidth() / 2);
			double centerY = nodeBounds.getMinY() + (nodeBounds.getHeight() / 2);
			
			double size = Math.max(nodeBounds.getWidth() + 20, nodeBounds.getHeight() + 20);

			RadialGradient shadePaint = new RadialGradient(
					0,0, centerX, centerY, size / 2, false, CycleMethod.NO_CYCLE,
					new Stop(0.98, Color.TRANSPARENT),
					new Stop(1, Color.rgb(0, 0, 0, 0.7))
					);
			
			return new Background(new BackgroundFill(shadePaint, null, Insets.EMPTY));
		}
		
	};


}
