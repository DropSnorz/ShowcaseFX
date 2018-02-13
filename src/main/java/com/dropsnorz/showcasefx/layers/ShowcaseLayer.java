package com.dropsnorz.showcasefx.layers;

import javafx.geometry.Bounds;
import javafx.scene.Node;

public interface ShowcaseLayer {
	
	public Node generateNode(double parentWidth, double parentHeight, Bounds nodeBounds);
	
}
