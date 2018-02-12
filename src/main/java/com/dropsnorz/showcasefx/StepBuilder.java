package com.dropsnorz.showcasefx;

import com.dropsnorz.showcasefx.layouts.ShowcaseLayout;

import javafx.scene.Node;

public class StepBuilder {
	
	protected ShowcaseStep step;
	
	
	StepBuilder(ShowcaseStep step){
		this.step = step;
	}
	
	StepBuilder(Node target, Node content){
		this.step = new ShowcaseStep(target, content);
	}
	
	
	public StepBuilder setLayer(ShowcaseBackground layer) {
		this.step.setBackground(layer);
		return this;
	}
	
	public StepBuilder setLayout(ShowcaseLayout layout) {
		this.step.setLayout(layout);
		return this;
	}
	
	public ShowcaseStep getStep() {
		return this.step;
	}
	
	

}
