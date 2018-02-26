package com.dropsnorz.showcasefx.layers;

import javafx.geometry.Bounds;
import javafx.scene.Node;

/**
 * Defines a ShowcaseLayer
 * A ShowcaseLayer object builds a JavaFx Node used to highlight a specific scene element
 *
 */
public interface ShowcaseLayer {
	
	/**
	 * Builds and returns the layer node.
	 * @param targetBounds Highlighted element's bounds
	 * @param parentWidth Showcase component width
	 * @param parentHeight Showcase component height
	 * @return layering node
	 */
	public Node getNode(Bounds targetBounds, double parentWidth, double parentHeight);
	
}
