package com.dropsnorz.showcasefx;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;

public class Showcase extends Pane {

	protected StackPane showcaseContainer;
	protected ArrayList<ShowcaseStep> steps;
	protected int currentStep;
	protected ChangeListener<Number> resizeListener;

	private static final String DEFAULT_STYLE_CLASS = "fx-showcase";

	public Showcase() {

		initialize();		
	}
	public Showcase(StackPane showCaseContainer) {

		initialize();
		this.setShowcaseContainer(showCaseContainer);
	}

	private void initialize() {
		this.setVisible(false);
		this.getStyleClass().add(DEFAULT_STYLE_CLASS);
		this.currentStep = - 1;
		this.steps = new ArrayList<ShowcaseStep>();

		this.resizeListener = new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
				updateShowcaseLayer();
			}
		};

	}

	public void start() {
		this.setVisible(true);
		this.widthProperty().addListener(resizeListener);
		this.heightProperty().addListener(resizeListener);

		next();

	}

	public void next() {

		this.currentStep = this.currentStep +  1;

		if(this.currentStep < this.steps.size()) {

			showStep();
		}
		else {
			stop();
		}
	}

	public void stop() {
		this.setVisible(false);
		this.widthProperty().removeListener(resizeListener);
		this.heightProperty().removeListener(resizeListener);
		this.currentStep = - 1;
	}

	private void showStep() {

		ShowcaseStep showcaseStep = this.steps.get(this.currentStep);

		this.getChildren().clear();

		updateShowcaseLayer();

	}

	private void updateShowcaseLayer() {

		ShowcaseStep showcaseStep = this.steps.get(this.currentStep);

		Node target = showcaseStep.getTargetNode();
		Bounds bounds = target.localToScene(target.getBoundsInLocal());

		double centerX = bounds.getMinX() + (bounds.getWidth() / 2);
		double centerY = bounds.getMinY() + (bounds.getHeight() / 2);

		double x_relative = centerX / this.showcaseContainer.getWidth();
		double y_relative = centerY / this.showcaseContainer.getHeight();

		RadialGradient shadePaint = new RadialGradient(
				0,0, x_relative, y_relative, 0.3, true, CycleMethod.NO_CYCLE,
				new Stop(1, Color.rgb(0, 0, 0, 0.6)),
				new Stop(0, Color.TRANSPARENT)
				);

		this.setBackground(new Background(new BackgroundFill(shadePaint, null, Insets.EMPTY)));


	}


	public void setShowcaseContainer(StackPane showcaseContainer) {
		if (showcaseContainer != null) {
			this.showcaseContainer = showcaseContainer;
			if (this.showcaseContainer.getChildren().indexOf(this) == -1
					|| this.showcaseContainer.getChildren().indexOf(this) != this.showcaseContainer.getChildren().size() - 1) {
				this.showcaseContainer.getChildren().remove(this);
				this.showcaseContainer.getChildren().add(this);
			}
		}
	}


	public void addStep(ShowcaseStep step) {
		steps.add(step);
	}

	public void addStep(Node targetNode, Node content) {
		steps.add(new ShowcaseStep(targetNode, content));
	}


}
