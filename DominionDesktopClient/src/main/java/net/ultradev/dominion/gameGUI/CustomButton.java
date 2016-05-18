package net.ultradev.dominion.gameGUI;

import javafx.geometry.Insets;
import javafx.scene.control.Button;

public class CustomButton {
	private Button button;

	public CustomButton(String text,double width,double height){
		button = new Button();
		button.setText(text);
		button.setPrefSize(width, height);

	}

	public CustomButton(String text){
		button = new Button();
		button.setText(text);
		button.setPrefHeight(36);
		button.setPadding(new Insets(0,10,0,10));

		setActive(true);
		button.setId("gameButton");
	}

	public Button getButton(){
		return button;
	}

	public void setActive(boolean isActive){
		if(isActive){
			button.setStyle("-fx-background-color:rgb(192,57,43);");
		}
		else{
			button.setStyle("-fx-background-color:gray;");
		}
	}
}
