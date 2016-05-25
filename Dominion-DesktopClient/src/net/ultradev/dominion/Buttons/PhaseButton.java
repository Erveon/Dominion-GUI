package net.ultradev.dominion.Buttons;


import javafx.geometry.Insets;
import javafx.scene.control.Button;
import net.ultradev.dominion.EventHandlers.*;
import net.ultradev.dominion.gameGUI.Hand;
import net.ultradev.dominion.gameGUI.PlayerBalk;


public class PhaseButton {
	private Button button;
	private PlayerBalk parent;
	private PhaseButtonEventHandler eventHandler;

	private Hand hand;

	public PhaseButton(PlayerBalk parent){
		this.parent = parent;
		createButton();
	}


	public void setHand(Hand hand){
		this.hand = hand;
	}

	public Hand getHand(){
		return hand;
	}

	public Button getButton(){
		return button;
	}
	public PlayerBalk getParent(){
		return parent;
	}

	public void mayStop(boolean b){
		eventHandler.setMayStop(b);
		setActive(b);

	}

	public void setActive(boolean active){
		if(active){
			button.setStyle("-fx-background-color: rgb(192,57,43);");
		}
		else{
			button.setStyle("-fx-background-color: gray;");
		}
	}


	public void changeButtonText(String text) {
		button.setText(text);
	}

	private void createButton(){
			button = new Button();
			button.setPrefHeight(36);
			button.setPadding(new Insets(0,10,0,10));
			button.setId("gameButton");
			button.setText("GO TO TREASURE PHASE");
			eventHandler = new PhaseButtonEventHandler(this);
			button.setOnAction(eventHandler);

	}












}
