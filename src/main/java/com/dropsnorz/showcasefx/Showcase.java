package com.dropsnorz.showcasefx;

import java.util.ArrayList;

import com.dropsnorz.showcasefx.events.ShowcaseEvent;
import com.dropsnorz.showcasefx.layers.ShowcaseLayer;
import com.dropsnorz.showcasefx.layers.ShowcaseLayerShape;
import com.dropsnorz.showcasefx.layouts.AutoShowcaseLayout;
import com.dropsnorz.showcasefx.layouts.ShowcaseLayout;
import com.dropsnorz.showcasefx.utils.BoundsUtils;

import com.dropsnorz.showcasefx.views.SimpleStepView;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.util.Duration;

public class Showcase extends StackPane {

	protected StackPane showcaseContainer;
	protected Pane backgroundPane;
	protected ShowcaseLayout defaultLayout;
	protected ShowcaseLayer defaultLayer;
	protected ArrayList<ShowcaseStep> steps;
	protected int currentStep;
	protected ChangeListener<Number> resizeListener;

	protected Node currentLayoutNode;

	protected int transitionDelay = 500;

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

		defaultLayout = new AutoShowcaseLayout();

		this.backgroundPane = new StackPane();

		this.setTraversableMask(false);

		this.getChildren().add(backgroundPane);

		this.defaultLayer = ShowcaseLayerShape.CIRCLE_FLAT;
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

		//this.contentContainer.setPickOnBounds(false);
		this.setPickOnBounds(false);

	}

	public void start() {
		this.setVisible(true);
		this.widthProperty().addListener(resizeListener);
		this.heightProperty().addListener(resizeListener);

		this.currentStep = 0;

		if(this.currentStep < this.steps.size()) {

			updateShowcaseLayer();
			Transition fadeIn = this.getFadeInTransition();
			fadeIn.setOnFinished(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					onShowcaseStartedProperty.get().handle(new ShowcaseEvent(ShowcaseEvent.STARTED));
					onShowcaseStepDisplayProperty.get().handle(new ShowcaseEvent(ShowcaseEvent.STEP_DISPLAY));
				}

			});

			fadeIn.play();
		}

	}

	public void next() {

		this.currentStep = this.currentStep +  1;

		if(this.currentStep < this.steps.size()) {
			switchStep();
		}
		else {
			stop();
		}
		
		
	}

	public void stop() {

		Transition fadeOut = this.getFadeOutTransition();
		fadeOut.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				setVisible(false);
				onShowcaseStoppedProperty.get().handle(new ShowcaseEvent(ShowcaseEvent.STOPPED));
				widthProperty().removeListener(resizeListener);
				heightProperty().removeListener(resizeListener);
				currentStep = - 1;
			}

		});
		fadeOut.play();

	}

	private void switchStep() {

		Transition fadeOut = this.getFadeOutTransition();
		fadeOut.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				showStep();
			}

		});
		fadeOut.play();

	}

	private void showStep() {		

		updateShowcaseLayer();
		Transition fadeIn = this.getFadeInTransition();
		fadeIn.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				onShowcaseStepDisplayProperty.get().handle(new ShowcaseEvent(ShowcaseEvent.STEP_DISPLAY));

			}

		});

		fadeIn.play();
	}

	private void updateShowcaseLayer() {

		if(currentStep < this.steps.size())
		{

			ShowcaseStep showcaseStep = this.steps.get(this.currentStep);

			Node target = showcaseStep.getTargetNode();

			Bounds targetBounds = showcaseContainer.sceneToLocal(target.localToScene(target.getBoundsInLocal()));

			Node backgroundNode;

			if(showcaseStep.getLayer() != null) {
				backgroundNode = showcaseStep.getLayer().generateNode(this.getWidth(), this.getHeight(), targetBounds);
			}
			else {
				backgroundNode = this.defaultLayer.generateNode(this.getWidth(), this.getHeight(), targetBounds);
			}


			this.backgroundPane.getChildren().clear();
			this.backgroundPane.getChildren().add(backgroundNode);

			Node contentNode = showcaseStep.getContent();

			ShowcaseLayout currentLayout;
			if(showcaseStep.getLayout() != null) {
				currentLayout = showcaseStep.getLayout();
			}
			else {

				currentLayout = this.defaultLayout;
			}

			currentLayout.addContentNode(contentNode, targetBounds, this.getWidth(), this.getHeight());

			if(currentLayoutNode !=null) {
				this.getChildren().remove(currentLayoutNode);
				currentLayoutNode.setOnMouseClicked(null);
			

			}
			this.currentLayoutNode = currentLayout.getNode();
			
			
			currentLayoutNode.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					processBehaviour(onClickBehaviour);		
				}
				
			});

			this.getChildren().add(currentLayoutNode);
		}


	}

	private void processBehaviour(ShowcaseBehaviour behaviour) {
		switch(behaviour) {

		case NEXT: next(); break;
		case CLOSE: stop(); break;
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


	public void setDefaultLayer(ShowcaseLayer layer) {
		this.defaultLayer = layer;
	}
	public ShowcaseLayout getDefaultLayout() {
		return defaultLayout;
	}
	public void setDefaultLayout(ShowcaseLayout layout) {
		this.defaultLayout = layout;
	}
	public void setTraversableMask(boolean traversable) {

		this.backgroundPane.setMouseTransparent(traversable);
	}


	public void addStep(ShowcaseStep step) {
		steps.add(step);
	}

	public StepBuilder createStep(Node targetNode, Node content) {
		StepBuilder builder = new StepBuilder(targetNode, content);
		steps.add(builder.getStep());

		return builder;
	}

	public StepBuilder createStep(Node target, String title, String content) {
		return createStep(target, new SimpleStepView(title, content));
	}

	/**
	 * 
	 * TRANSITIONS
	 * 
	 */


	private Transition getFadeInTransition() {
		FadeTransition ft = new FadeTransition(Duration.millis(transitionDelay), this);
		ft.setFromValue(0);
		ft.setToValue(1);
		ft.setCycleCount(1);

		return ft;
	}

	private Transition getFadeOutTransition() {
		FadeTransition ft = new FadeTransition(Duration.millis(transitionDelay), this);
		ft.setFromValue(1);
		ft.setToValue(0);
		ft.setCycleCount(1);

		return ft;
	}

	/**
	 * Set the Showcase transition animation delay in miliseconds.
	 * @param delay
	 *
	 * The delay value is applied to fade-in and fade-out animations.
	 */
	public void setTransitionDelay(int delay) {
		this.transitionDelay = delay;
	}


	/**
	 * 
	 * EVENTS
	 * 
	 */


	private ObjectProperty<EventHandler<? super ShowcaseEvent>> onShowcaseStartedProperty = new SimpleObjectProperty<>((started) -> {
	});

	/**
	 * Defines a function to be called when the showcase is started.
	 * It will be triggered after the start animation is finished.
	 */
	public void setOnShowcaseStarted(EventHandler<? super ShowcaseEvent> handler) {
		onShowcaseStartedProperty.set(handler);
	}

	public EventHandler<? super ShowcaseEvent> getOnShowcaseStarted() {
		return onShowcaseStartedProperty.get();
	}


	private ObjectProperty<EventHandler<? super ShowcaseEvent>> onShowcaseStoppedProperty = new SimpleObjectProperty<>((stopped) -> {
	});

	/**
	 * Defines a function to be called when the showcase is stopped.
	 * It will be triggered after the close animation is finished.
	 */
	public void setOnShowcaseStopped(EventHandler<? super ShowcaseEvent> handler) {
		onShowcaseStoppedProperty.set(handler);
	}

	public EventHandler<? super ShowcaseEvent> getOnShowcaseStopped() {
		return onShowcaseStoppedProperty.get();
	}


	private ObjectProperty<EventHandler<? super ShowcaseEvent>> onShowcaseStepDisplayProperty = new SimpleObjectProperty<>((stopped) -> {
	});

	/**
	 * Defines a function to be called when the showcase is stopped.
	 * It will be triggered after the close animation is finished.
	 */
	public void setOnShowcaseStepDisplay(EventHandler<? super ShowcaseEvent> handler) {
		onShowcaseStepDisplayProperty.set(handler);
	}

	public EventHandler<? super ShowcaseEvent> getOnShowcaseStepDisplay() {
		return onShowcaseStepDisplayProperty.get();
	}


}
