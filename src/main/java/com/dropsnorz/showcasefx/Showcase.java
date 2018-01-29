package com.dropsnorz.showcasefx;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;

public class Showcase extends StackPane {

	protected StackPane showcaseContainer;
	protected Pane contentContainer;
	protected Pane backgroundPane;
	protected ShowcaseBackground background;
	protected ArrayList<ShowcaseStep> steps;
	protected int currentStep;
	protected ChangeListener<Number> resizeListener;
	
	protected ShowcaseBehaviour onClickBehaviour =  ShowcaseBehaviour.NEXT;

	private static final String DEFAULT_STYLE_CLASS = "fx-showcase";

	public enum ShowcaseBehaviour{
		NEXT,
		CLOSE,
		NONE,
	}

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
		
		this.contentContainer = new Pane();
		this.backgroundPane = new StackPane();
		
		this.setTraversableMask(false);
				
		this.getChildren().add(backgroundPane);
		this.getChildren().add(contentContainer);

		this.background = ShowcaseBackgroundShape.CIRCLE_FLAT;
		this.currentStep = - 1;
		this.steps = new ArrayList<ShowcaseStep>();

		this.resizeListener = new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
				updateShowcaseLayer();
			}
		};

		this.backgroundPane.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			public void handle(MouseEvent e) {
				processBehaviour(onClickBehaviour);

			}
		});
		
		this.contentContainer.setPickOnBounds(false);
		this.setPickOnBounds(false);
		
		

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
			close();
		}
	}

	public void close() {
		this.setVisible(false);
		this.widthProperty().removeListener(resizeListener);
		this.heightProperty().removeListener(resizeListener);
		this.currentStep = - 1;
	}

	private void showStep() {

		ShowcaseStep showcaseStep = this.steps.get(this.currentStep);

		this.contentContainer.getChildren().clear();

		updateShowcaseLayer();

	}

	private void updateShowcaseLayer() {

		ShowcaseStep showcaseStep = this.steps.get(this.currentStep);

		Node target = showcaseStep.getTargetNode();
		
		Bounds bounds = showcaseContainer.sceneToLocal(target.localToScene(target.getBoundsInLocal()));
		
		Node backgroundNode;
		
		if(showcaseStep.getBackground() != null) {
			backgroundNode = showcaseStep.getBackground().generateNode(this.getWidth(), this.getHeight(), bounds);
		}
		else {
			backgroundNode = this.background.generateNode(this.getWidth(), this.getHeight(), bounds);
		}
		
		 
		this.backgroundPane.getChildren().clear();
		this.backgroundPane.getChildren().add(backgroundNode);


	}

	private void processBehaviour(ShowcaseBehaviour behaviour) {
		switch(behaviour) {

		case NEXT: next(); break;
		case CLOSE: close(); break;
		default: break;
		}
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
	
	public void setTraversableMask(boolean traversable) {
		
		this.backgroundPane.setMouseTransparent(traversable);
	}


	public void addStep(ShowcaseStep step) {
		steps.add(step);
	}

	public void addStep(Node targetNode, Node content) {
		steps.add(new ShowcaseStep(targetNode, content));
	}
	
	public void addStep(Node targetNode, Node content, ShowcaseBackground background) {
		ShowcaseStep step = new ShowcaseStep(targetNode, content);
		step.setBackground(background);
		steps.add(step);
		
	}


}
