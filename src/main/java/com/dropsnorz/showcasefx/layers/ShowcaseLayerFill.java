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

/**
 * A ShowcaseLayerFill is a Layer node generator based on JavaFx backgrounds
 * The generated background will be converted as a layer node
 */
public abstract class ShowcaseLayerFill implements ShowcaseLayer {
	
	/**
	 * Builds and returns the background
	 * @param targetBounds Highlighted element's bounds
	 * @param parentWidth Showcase component width
	 * @param parentHeight Showcase component height
	 * @return layering background
	 */
	public abstract Background generate(Bounds targetBounds, double parentWidth, double parentHeight);
	
	public Node getNode(Bounds targetBounds, double parentWidth, double parentHeight) {
		
		Background background = this.generate(targetBounds, parentWidth, parentHeight);
		Pane pane = new Pane();
		pane.setBackground(background);
		
		return pane;
	}
	
		
	public static ShowcaseLayerFill CIRCLE_GRADIENT = new ShowcaseLayerFill() {

		@Override
		public Background generate(Bounds targetBounds, double parentWidth, double parentHeight) {
			
			double centerX = targetBounds.getMinX() + (targetBounds.getWidth() / 2);
			double centerY = targetBounds.getMinY() + (targetBounds.getHeight() / 2);

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
		public Background generate(Bounds targetBounds, double parentWidth, double parentHeight) {
			
			double centerX = targetBounds.getMinX() + (targetBounds.getWidth() / 2);
			double centerY = targetBounds.getMinY() + (targetBounds.getHeight() / 2);
			double size = Math.max(targetBounds.getWidth() + 20, targetBounds.getHeight() + 20);

			RadialGradient shadePaint = new RadialGradient(
					0,0, centerX, centerY, size / 2, false, CycleMethod.NO_CYCLE,
					new Stop(0.98, Color.TRANSPARENT),
					new Stop(1, Color.rgb(0, 0, 0, 0.7))
					);
			
			return new Background(new BackgroundFill(shadePaint, null, Insets.EMPTY));
		}
		
	};
}
