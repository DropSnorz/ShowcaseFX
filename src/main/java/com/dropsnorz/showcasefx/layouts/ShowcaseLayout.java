package com.dropsnorz.showcasefx.layouts;

import javafx.geometry.Bounds;
import javafx.scene.Node;

public abstract class ShowcaseLayout {
	
	public abstract void addContentNode(Node content,  Bounds targetBoundsInParent, double parentWidth, double parentHeight);
	
	public abstract Node getNode();
	

}
