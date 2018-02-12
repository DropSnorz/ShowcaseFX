package com.dropsnorz.showcasefx;


import com.dropsnorz.showcasefx.layouts.ShowcaseLayout;

import javafx.scene.Node;

public class ShowcaseStep {
	
	protected Node targetNode;
	protected Node content;
	protected ShowcaseBackground background = null;
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


	public ShowcaseBackground getBackground() {
		return background;
	}


	public void setBackground(ShowcaseBackground background) {
		this.background = background;
	}



	public ShowcaseLayout getLayout() {
		return layout;
	}

	public void setLayout(ShowcaseLayout layout) {
		this.layout = layout;
	}
	

}
