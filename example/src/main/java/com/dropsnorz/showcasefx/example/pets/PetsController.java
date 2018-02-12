package com.dropsnorz.showcasefx.example.pets;

import java.net.URL;
import java.util.ResourceBundle;

import com.dropsnorz.showcasefx.Showcase;
import com.dropsnorz.showcasefx.ShowcaseBackgroundShape;
import com.dropsnorz.showcasefx.ShowcaseStep;
import com.dropsnorz.showcasefx.layouts.AutoShowcaseLayout;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class PetsController implements Initializable {
	
	@FXML
	StackPane rootPane;
	@FXML
	JFXButton helpButton;
	@FXML
	JFXButton leaveButton;
	@FXML
	JFXTextField nameTextField;
	@FXML
	JFXComboBox typeComboBox;
	@FXML
	JFXButton addButton;
	@FXML
	JFXListView petsList;
	
	Showcase showcase;

	public void initialize(URL location, ResourceBundle resources) {
		
		showcase = new Showcase(rootPane);
		
		showcase.setDefaultBackground(ShowcaseBackgroundShape.RECTANGLE_FLAT);
		
		AutoShowcaseLayout bigLayout = new AutoShowcaseLayout();
		bigLayout.setRowOffset(-1);
		bigLayout.setRowSpan(2);
		
		showcase.createStep(nameTextField, "Create a new Pet #1", "Write the name of your pet here.");
		showcase.createStep(typeComboBox, "Create a new Pet #2", "Select the type of your pet.");
		showcase.createStep(addButton, "Create a new Pet #3", "When you have filled all informations, just press the add button");
				
		showcase.createStep(petsList, "View all Pet", "After adding a pet, it will be display here, with all his previsously added friends").setLayout(bigLayout);
				
		showcase.createStep(leaveButton, "Bye !", "You can close the app by clicking the Leave button");
		
		helpButton.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				
				showcase.start();
				
			}
			
		});

	}

}
