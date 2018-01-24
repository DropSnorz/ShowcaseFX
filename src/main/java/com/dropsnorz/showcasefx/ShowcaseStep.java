package com.dropsnorz.showcasefx;

import javafx.scene.Node;

public class ShowcaseStep {
	
	protected Node targetNode;
	protected Node content;
	
	
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
	
	

}
