package com.dropsnorz.showcasefx;

import java.util.ArrayList;

import com.dropsnorz.showcasefx.events.ShowcaseEvent;
import com.dropsnorz.showcasefx.layers.ShowcaseLayer;
import com.dropsnorz.showcasefx.layers.ShowcaseLayerShape;
import com.dropsnorz.showcasefx.layouts.AutoShowcaseLayout;
import com.dropsnorz.showcasefx.layouts.ShowcaseLayout;
import com.dropsnorz.showcasefx.views.SimpleStepView;

import javafx.animation.Animation.Status;
import javafx.animation.FadeTransition;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * Note: for Showcase to work properly, the root node MUST
 * be of type {@link StackPane}
 *
 */
public class Showcase extends StackPane {

	protected StackPane showcaseContainer;
	protected Pane layerPane;
	protected ShowcaseLayout defaultLayout;
	protected ShowcaseLayer defaultLayer;
	protected ArrayList<ShowcaseStep> steps;
	protected int currentStep;

	protected ChangeListener<Bounds> boundsListener;
	protected EventHandler<MouseEvent> clickHandler;

	protected Node currentLayoutNode;
	private Node mountedTarget;
	private ShowcaseLayer mountedLayer;
	private ShowcaseLayout mountedLayout;

	protected ShowcaseBehaviour onClickBehaviour =  ShowcaseBehaviour.NEXT;
	protected boolean updateOnTargetBoundsChange = true;

	private FadeTransition fadeIn;
	private FadeTransition fadeOut;

	private static final String DEFAULT_STYLE_CLASS = "showcase";
	private static final String CONTENT_STYLE_CLASS = "showcase-step-content";


	public enum ShowcaseBehaviour{
		NEXT,
		CLOSE,
		NONE,
	}

	/**
	 * Creates a Showcase control
	 */
	public Showcase() {
		initialize();		
	}
	
	/**
	 * Creates a Showcase control with the specified root container
	 * @param showCaseContainer root container
	 */
	public Showcase(StackPane showCaseContainer) {
		initialize();
		this.setShowcaseContainer(showCaseContainer);
	}

	private void initialize() {
		this.setVisible(false);
		this.getStyleClass().add(DEFAULT_STYLE_CLASS);

		defaultLayout = new AutoShowcaseLayout();

		this.layerPane = new StackPane();

		this.getChildren().add(layerPane);

		this.defaultLayer = ShowcaseLayerShape.CIRCLE_FLAT;
		this.currentStep = - 1;
		this.steps = new ArrayList<ShowcaseStep>();

		this.boundsListener = new ChangeListener<Bounds>() {
			public void changed(ObservableValue<? extends Bounds> observableValue, Bounds oldBounds, Bounds newBounds) {
				updateShowcaseLayer();

			}
		};
		


		this.clickHandler = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				if(fadeIn.getStatus() == Status.RUNNING || fadeOut.getStatus() == Status.RUNNING) {
					fadeIn.jumpTo("end");
					fadeOut.jumpTo("end");

				} else {
					processBehaviour(onClickBehaviour);
				}
			}
		};

		this.layerPane.addEventHandler(MouseEvent.MOUSE_CLICKED, clickHandler);

		this.fadeIn = new FadeTransition(Duration.millis(500), this);
		fadeIn.setFromValue(0);
		fadeIn.setToValue(1);
		fadeIn.setCycleCount(1);
		this.fadeOut = new FadeTransition(Duration.millis(500), this);
		fadeOut.setFromValue(1);
		fadeOut.setToValue(0);
		fadeOut.setCycleCount(1);

		this.setPickOnBounds(false);
	}

	/**
	 * Start the showcase component and display the first step
	 */
	public void start() {
		
		//TODO Fix start call when component is already started
		this.setVisible(true);
	
		this.layoutBoundsProperty().addListener(boundsListener);

		this.currentStep = 0;

		if(this.currentStep < this.steps.size()) {

			mountStep();
			updateShowcaseLayer();
			fadeIn.setOnFinished(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					onShowcaseStartedProperty.get().handle(new ShowcaseEvent(ShowcaseEvent.STARTED));
					onShowcaseStepDisplayProperty.get().handle(new ShowcaseEvent(ShowcaseEvent.STEP_DISPLAY));
				}
			});

			fadeIn.playFromStart();
		}
	}


	/**
	 * Displays the next step. If the last step has been reached the stop() method is called
	 * NOTE: The Showcase component <b>must be started</b> by calling start() before any call to next()
	 */
	public void next() {

		if(isStarted()) {
			this.currentStep = this.currentStep +  1;
			if(this.currentStep < this.steps.size()) {
				switchStep();
			} else {
				stop();
			}

		} else {
			throw new IllegalStateException("Showcase component must be started");
		}
	}


	/**
	 * 	/**
	 * Displays the specified step.
	 * NOTE: The Showcase component <b>must be started</b> by calling start() before any call to jumpTo()
	 * @param stepIndex 	the step index to display
	 */
	public void jumpTo(int stepIndex) {

		if(isStarted() && stepIndex < this.steps.size() && stepIndex >= 0) {
			this.currentStep = stepIndex;
			switchStep();
		}
		else if(!isStarted()) {
			throw new IllegalStateException("Showcase component must be started");
		}
		else {
			throw new IndexOutOfBoundsException("Index " + stepIndex + " is out of bounds!");
		}
	}

	/**
	 * Stop the Showcase component. Automatically called when the last step have been reached
	 * NOTE: The Showcase component <b>must be</b> started
	 */
	public void stop() {

		if(isStarted()) {
			fadeIn.stop();
			fadeOut.setOnFinished(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					unmountStep();
					setVisible(false);
					onShowcaseStoppedProperty.get().handle(new ShowcaseEvent(ShowcaseEvent.STOPPED));
					layoutBoundsProperty().removeListener(boundsListener);

					currentStep = - 1;
				}
			});
			fadeOut.playFromStart();
		}
		else {
			throw new IllegalStateException("Showcase component must be started");
		}
	}

	private void switchStep() {

		fadeOut.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				showStep();
			}
		});
		fadeOut.playFromStart();
	}

	private void showStep() {		

		mountStep();
		updateShowcaseLayer();
		fadeIn.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				onShowcaseStepDisplayProperty.get().handle(new ShowcaseEvent(ShowcaseEvent.STEP_DISPLAY));

			}
		});

		fadeIn.playFromStart();
	}

	private void mountStep() {

		unmountStep();
		
		ShowcaseStep showcaseStep = this.steps.get(this.currentStep);
		mountedTarget = showcaseStep.getTargetNode();

		if(showcaseStep.getLayer() != null) {
			mountedLayer = showcaseStep.getLayer();
		} else {
			mountedLayer = this.defaultLayer;
		}

		if(showcaseStep.getLayout() != null) {
			mountedLayout = showcaseStep.getLayout();
		} else {
			mountedLayout = this.defaultLayout;
		}

		if(updateOnTargetBoundsChange) {
			mountedTarget.boundsInParentProperty().addListener(boundsListener);
		}
	}

	private void unmountStep() {

		if(mountedTarget != null) {
			mountedTarget.boundsInParentProperty().removeListener(boundsListener);
		}
	}
	

	private synchronized void updateShowcaseLayer() {
		

		if(currentStep < this.steps.size())
		{
			this.applyCss();
			this.layout();
			double width = this.getWidth();
			double height = this.getHeight();
			
			ShowcaseStep showcaseStep = this.steps.get(this.currentStep);

			Bounds targetBounds = showcaseContainer.sceneToLocal(mountedTarget.localToScene(mountedTarget.getBoundsInLocal()));

			
			this.layerPane.getChildren().clear();
			if(mountedLayer != null) {
				Node layerNode = this.mountedLayer.getNode(targetBounds,width, height);
				this.layerPane.getChildren().add(layerNode);
			}
			
			Node contentNode = showcaseStep.getContent();
			contentNode.getStyleClass().add(CONTENT_STYLE_CLASS);

			if(currentLayoutNode !=null) {
				this.getChildren().remove(currentLayoutNode);
				currentLayoutNode.setOnMouseClicked(null);
			}

			this.currentLayoutNode = mountedLayout.getNode();
			this.getChildren().add(currentLayoutNode);

			currentLayoutNode.setOnMouseClicked(clickHandler);
						
			mountedLayout.addContentNode(contentNode, targetBounds, width, height);

			
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


	/**
	 * Set the default layer used if the current {@link ShowcaseStep} doesn't provide any layer object
	 * @param layer		the default layer
	 */
	public void setDefaultLayer(ShowcaseLayer layer) {
		this.defaultLayer = layer;
	}

	/**
	 *Get the default layer used if the current {@link ShowcaseStep} doesn't provide any layer object
	 * @return the default layer
	 */
	public ShowcaseLayer getDefaultLayer() {
		return defaultLayer;
	}
	
	/**
	 * Set the default layout used if the current {@link ShowcaseStep} doesn't provide any layout object
	 * @param layout		the default layout
	 */
	public void setDefaultLayout(ShowcaseLayout layout) {
		this.defaultLayout = layout;
	}
	
	/**
	 *Get the default layout used if the current {@link ShowcaseStep} doesn't provide any layout object
	 * @return the default layout
	 */
	public ShowcaseLayout getDefaultLayout() {
		return defaultLayout;
	}
	
	/**
	 * Add the given Step to the step list
	 * @param step
	 */
	public void addStep(ShowcaseStep step) {
		steps.add(step);
	}
	
	/**
	 * Generate a {@link StepBuilder} and add it's related step to the Showcase
	 * @param targetNode		the target node
	 * @param content			the content node
	 * @return the step builder
	 */
	public StepBuilder createStep(Node targetNode, Node content) {
		StepBuilder builder = new StepBuilder(targetNode, content);
		steps.add(builder.getStep());
		return builder;
	}
	
	/**
	 * Generate a {@link StepBuilder} based on a SimpleStepView component and add the related step to the Showcase
	 * @param targetNode		the target node
	 * @param title				Title of the SimpleStepView
	 * @param body			Body of the simple step view
	 * @return the step builder
	 */
	public StepBuilder createStep(Node targetNode, String title, String body) {
		return createStep(targetNode, new SimpleStepView(title, body));
	}
	/**
	 * Generate a {@link StepBuilder} based on a SimpleStepView component and add the related step to the Showcase
	 * @param targetNode		the target node
	 * @param body				Body of the SimpleStepView
	 * @return the step builder
	 */
	public StepBuilder createStep(Node targetNode, String body) {
		return createStep(targetNode, new SimpleStepView("", body));
	}
	public int getCurrentPosition() {
		return this.currentStep;
	}
	public ShowcaseStep getCurrentStep() {
		if(this.currentStep > 0 && this.currentStep < this.steps.size()) {
			return this.steps.get((this.currentStep));
		}
		return null;
	}
	public boolean isStarted() {
		return this.currentStep >= 0;
	}
	public boolean isUpdateOnTargetBoundsChange() {
		return updateOnTargetBoundsChange;
	}
	public void setOnClickBehaviour(ShowcaseBehaviour onClickBehaviour) {
		this.onClickBehaviour = onClickBehaviour;
	}

	/**
	 * Sets the value of updateOnTargetBoundsChange property.
	 * Defines if the showcase should be updated if target node's bounds changes. Default value: true
	 * @param updateOnTargetBoundsChange
	 */
	public void setUpdateOnTargetBoundsChange(boolean updateOnTargetBoundsChange) {
		this.updateOnTargetBoundsChange = updateOnTargetBoundsChange;
			}


	/**
	 * 
	 * TRANSITIONS
	 * 
	 */

	/**
	 * Set the Showcase transition animation delay in miliseconds.
	 * @param delay
	 *
	 * The delay value is applied to fade-in and fade-out animations.
	 */
	public void setTransitionDelay(int delay) {
		this.fadeIn.setDelay(Duration.millis(delay));
		this.fadeOut.setDelay(Duration.millis(delay));
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
