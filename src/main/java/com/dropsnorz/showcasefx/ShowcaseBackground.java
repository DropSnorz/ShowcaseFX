package com.dropsnorz.showcasefx;

import javafx.geometry.Bounds;
import javafx.scene.Node;

public interface ShowcaseBackground {
	
	public Node generateNode(double parentWidth, double parentHeight, Bounds nodeBounds);
	
}
