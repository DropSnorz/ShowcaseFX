package com.dropsnorz.showcasefx.layers;


import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.shape.Shape;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * A ShowcaseLayerShape is a Layer node generator based on JavaFx shapes
 * The generated shape is substracted to a rectangle background sized as the showcase components.
 */
public abstract class ShowcaseLayerShape implements ShowcaseLayer {
	
	private static final String LAYER_STYLE_CLASS = "showcase-layer";
	private static final String SHAPE_STYLE_CLASS = "showcase-layer-shape";

	/**
	 * Builds and returns the highlight shape
	 * @param targetBounds Highlighted element's bounds
	 * @param parentWidth Showcase component width
	 * @param parentHeight Showcase component height
	 * @return highlight shape
	 */
	public abstract Shape generate(Bounds targetBounds, double parentWidth, double parentHeight);
	
	public Node getNode(Bounds targetBounds, double parentWidth, double parentHeight) {
		
		Shape clip = this.generate(targetBounds, parentWidth, parentHeight);
		clip.setFill(Color.rgb(0, 0, 0, 0.6));

		Pane pane = new Pane();
		
		final Rectangle inverse = new Rectangle();
		inverse.setWidth( parentWidth );
		inverse.setHeight( parentHeight);
		Shape shape = Shape.subtract( inverse, clip );
		shape.getStyleClass().add(SHAPE_STYLE_CLASS);
		shape.setFill(Color.rgb(0, 0, 0, 0.7));

		shape.getStyleClass().add(LAYER_STYLE_CLASS);
		pane.getChildren().add(shape);
		
		return pane;
	}
	
	
	public static ShowcaseLayerShape CIRCLE_FLAT = new ShowcaseLayerShape() {

		@Override
		public Shape generate(Bounds targetBounds, double parentWidth, double parentHeight) {
			
			double centerX = targetBounds.getMinX() + (targetBounds.getWidth() / 2);
			double centerY = targetBounds.getMinY() + (targetBounds.getHeight() / 2);
			int radiusOffset = 8;
			double radius = Math.max((targetBounds.getWidth() / 2 + radiusOffset), (targetBounds.getHeight() / 2 + radiusOffset));

			Circle circle = new Circle(centerX, centerY, radius);
			return circle;
		}
		
	};
	
	public static ShowcaseLayerShape RECTANGLE_FLAT = new ShowcaseLayerShape() {

		@Override
		public Shape generate(Bounds targetBounds, double parentWidth, double parentHeight) {
			
			int offset = 10;
			Rectangle rectangle = new Rectangle(targetBounds.getMinX() - offset, 
					targetBounds.getMinY() - offset, 
					targetBounds.getWidth() + 2*offset, 
					targetBounds.getHeight() + 2*offset);
			return rectangle;
		}
		
	};
}
