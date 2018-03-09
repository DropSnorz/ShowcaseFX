package com.dropsnorz.showcasefx;


import com.dropsnorz.showcasefx.layers.ShowcaseLayer;
import com.dropsnorz.showcasefx.layouts.ShowcaseLayout;

import javafx.scene.Node;

public class ShowcaseStep {
	
	protected Node targetNode;
	protected Node content;
	protected ShowcaseLayer layer = null;
	protected ShowcaseLayout layout = null;
	
	
	public ShowcaseStep(Node targetNode, Node content) {
		
		this.targetNode = targetNode;
		this.content = content;
	}


	public Node getTargetNode() {
		return targetNode;
	}


	public void setTargetNode(Node targetNode) {
		this.targetNode = targetNode;
	}


	public Node getContent() {
		return content;
	}


	public void setContent(Node content) {
		this.content = content;
	}


	public ShowcaseLayer getLayer() {
		return layer;
	}


	public void setLayer(ShowcaseLayer layer) {
		this.layer = layer;
	}



	public ShowcaseLayout getLayout() {
		return layout;
	}

	public void setLayout(ShowcaseLayout layout) {
		this.layout = layout;
	}
	

}
