package com.dropsnorz.showcasefx.example.pets;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import com.dropsnorz.showcasefx.Showcase;
import com.dropsnorz.showcasefx.ShowcaseStep;
import com.dropsnorz.showcasefx.layers.ShowcaseLayerShape;
import com.dropsnorz.showcasefx.layouts.AutoShowcaseLayout;
import com.dropsnorz.showcasefx.layouts.RelativeShowcaseLayout;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
	JFXComboBox<PetType> typeComboBox;
	@FXML
	JFXButton addButton;
	@FXML
	JFXButton randomizeButton;
	@FXML
	JFXListView<Pet> petsList;
	
	
	ObservableList<Pet> petsCollection;
	
	Showcase showcase;
	
	
	protected List<PetType> types = new ArrayList<PetType>();
	protected List<String> randomNames = Arrays.asList("Augusta","Lanora","Nathanial","Harrison", "Mica","Chana","Mayola","Hope",
    "Ava","Kandice","Roosevelt","Margareth","Delana","Paulene","Rosaria","Jack","Brooke","Lionel","Ann","Marisha");

	public void initialize(URL location, ResourceBundle resources) {
		
		
		//INIT DATA
		
		types.add(new PetType("Cat", "🐱"));
		types.add(new PetType("Dog", "🐶"));
		types.add(new PetType("Horse", "🐴"));
		types.add(new PetType("Unicorn", "🦄"));
		types.add(new PetType("Hamster", "🐹"));
		types.add(new PetType("Rabbit", "🐰"));
		types.add(new PetType("Fox", "🦊"));

		typeComboBox.getItems().addAll(types);
		
		
		this.petsCollection = FXCollections.observableArrayList ();
		petsCollection.add(new Pet("Lex", new PetType("Cat", "🐱")));
		
		
		petsList.setItems(petsCollection);
		
		addButton.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				
				PetType selectedType = typeComboBox.getValue();
				String name = nameTextField.getText();
				
				if(selectedType != null && name != null && !name.equals("")) {
					petsCollection.add(new Pet(name, selectedType));
					nameTextField.setText("");

				}
				
			}
			
		});
		
		randomizeButton.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				randomizeInputs();
			}
		});

		
		showcase = new Showcase(rootPane);
		
		showcase.setDefaultLayer(ShowcaseLayerShape.RECTANGLE_FLAT);
		
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
	
	public void randomizeInputs() {
		
		int maxTypeIndex = this.typeComboBox.getItems().size();
		int maxNameIndex = this.randomNames.size();
		
		Random randomGenerator = new Random();
		
		this.typeComboBox.getSelectionModel().select(randomGenerator.nextInt(maxTypeIndex));
		this.nameTextField.setText(randomNames.get(randomGenerator.nextInt(maxNameIndex)));
		
	}

}
