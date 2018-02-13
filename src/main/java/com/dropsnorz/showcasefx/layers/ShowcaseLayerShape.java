package com.dropsnorz.showcasefx.layers;


import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.shape.Shape;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public abstract class ShowcaseLayerShape implements ShowcaseLayer {
	
	public abstract Shape generate(double parentWidth, double parentHeight, Bounds nodeBounds);
	
	public Node generateNode(double parentWidth, double parentHeight, Bounds nodeBounds) {
		
		Shape clip = this.generate(parentWidth, parentHeight, nodeBounds);
		clip.setFill(Color.rgb(0, 0, 0, 0.6));

		Pane pane = new Pane();
		
		final Rectangle inverse = new Rectangle();
		inverse.setWidth( parentWidth );
		inverse.setHeight( parentHeight);
		Shape shape = Shape.subtract( inverse, clip );
		shape.setFill(Color.rgb(0, 0, 0, 0.7));

		pane.getChildren().add(shape);
		
		
		return pane;
	}
	
	
	private void setInverseClip( final Node node, final Shape clip ) {
		final Rectangle inverse = new Rectangle();
		inverse.setWidth( node.getLayoutBounds().getWidth() );
		inverse.setHeight( node.getLayoutBounds().getHeight() );
		node.setClip( Shape.subtract( inverse, clip ) );
	}
	
	public static ShowcaseLayerShape CIRCLE_FLAT = new ShowcaseLayerShape() {

		@Override
		public Shape generate(double parentWidth, double parentHeight, Bounds nodeBounds) {
			
			double centerX = nodeBounds.getMinX() + (nodeBounds.getWidth() / 2);
			double centerY = nodeBounds.getMinY() + (nodeBounds.getHeight() / 2);
			
			int radiusOffset = 8;
			
			double radius = Math.max((nodeBounds.getWidth() / 2 + radiusOffset), (nodeBounds.getHeight() / 2 + radiusOffset));

			Circle circle = new Circle(centerX, centerY, radius);
			return circle;
		}
		
	};
	
	public static ShowcaseLayerShape RECTANGLE_FLAT = new ShowcaseLayerShape() {

		@Override
		public Shape generate(double parentWidth, double parentHeight, Bounds nodeBounds) {
			
			int offset = 10;
			
			Rectangle rectangle = new Rectangle(nodeBounds.getMinX() - offset, 
					nodeBounds.getMinY() - offset, 
					nodeBounds.getWidth() + 2*offset, 
					nodeBounds.getHeight() + 2*offset);
			return rectangle;
		}
		
	};


}
