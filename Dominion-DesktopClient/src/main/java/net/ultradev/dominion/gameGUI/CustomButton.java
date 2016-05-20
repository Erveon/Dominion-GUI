package net.ultradev.dominion.gameGUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import net.sf.json.JSONObject;
import net.ulradev.dominion.EventHandlers.*;
import net.ultradev.dominion.game.Turn;


public class CustomButton {
	private Button button;
	private PlayerBalk parent;
	private EventHandler<ActionEvent> eventHandler;

	private Turn turn;
	private Hand hand;
	private String type;

	public CustomButton(String type, PlayerBalk parent){
		this.parent = parent;
		turn = parent.getParent().getTurn();
		this.type = type;
		createButton(type);
		//System.out.println(parent.getParent().getHand());
	}

	public CustomButton(Turn turn, Hand hand, String type){
		this.turn = turn;
		setHand(hand);
		this.parent = null;
		this.type = type;
		createButton(type);
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

	public void setActive(boolean active){
		if(!active){
			button.setStyle("-fx-background-color:gray;");
		}
		else{
			button.setStyle("-fx-background-color:rgb(192,57,43);");
		}
	}


	public void setAction(JSONObject response) {
		if(eventHandler instanceof ActionButtonEventHandler ){
			setActive(true);
			((ActionButtonEventHandler) eventHandler).setAction(response);
			changeButtonText(response);
		}

	}

	public void changeButtonText(JSONObject response) {
		if(response != null && type.equals("action")){
			switch(response.getString("result").toString()){
			case "SELECT_CARD_HAND":
				if(response.getString("player").equals(hand.getPlayer().getDisplayname())){
					if(turn.getActiveAction().getDescripton().toLowerCase().contains("trash")){
						getButton().setText("TRASH CARD");
					}
				}else{
					getButton().setText("NEXT PLAYER");
				}
				break;
			case "SELECT_CARD_BOARD":
				getButton().setText("GET CARD (Cost: " + response.getString("cost")+")");
				break;
			case "DONE":
				getButton().setText("DONE");
				break;
			}
		}


	}

	private void createButton(String type){
			button = new Button();
			button.setPrefHeight(36);
			button.setPadding(new Insets(0,10,0,10));
			button.setId("gameButton");
			setEventHandler(type);

	}

	private void setEventHandler(String type){
		switch(type){
			case "action":
				button.setText("DISCARD CARD");
				setActive(false);
				eventHandler = new ActionButtonEventHandler(this, turn);
				break;

			case "play":
				button.setText("PLAY CARD");
				setActive(false);
				eventHandler = new PlayButtonEventHandler(this);
				break;

			case "phase":
				button.setText("GO TO TREASURE PHASE");
				eventHandler = new PhaseButtonEventHandler(this);
				break;
			case "stopAction":
				button.setText("STOP ACTION");
				setActive(false);
				eventHandler = new StopActionButtonEventHandler(this);
				break;
		}
		button.setOnAction(eventHandler);
	}











}
