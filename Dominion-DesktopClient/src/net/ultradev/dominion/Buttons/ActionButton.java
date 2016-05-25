package net.ultradev.dominion.Buttons;


import javafx.geometry.Insets;
import javafx.scene.control.Button;
import net.sf.json.JSONObject;
import net.ultradev.dominion.EventHandlers.*;
import net.ultradev.dominion.game.Turn;
import net.ultradev.dominion.gameGUI.Hand;
import net.ultradev.dominion.gameGUI.PlayerBalk;


public class ActionButton {
	private Button button;
	private PlayerBalk parent;
	private ActionButtonEventHandler eventHandler;

	private Turn turn;
	private Hand hand;

	public ActionButton(PlayerBalk parent){
		this.parent = parent;
		turn = parent.getParent().getTurn();
		createButton();
	}

	public ActionButton(Turn turn,JSONObject response){
		this.turn = turn;
		this.parent = null;
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

	public void playCard(){
		eventHandler.playCard();
	}
	public void buyCard(){
		eventHandler.buyCard();
	}
	public void setActive(boolean active){
		if(active){
			button.setStyle("-fx-background-color: rgb(192,57,43);");
		}
		else{
			button.setStyle("-fx-background-color: gray;");
		}
	}

	public void changeButtonText(JSONObject response) {
		button.setText(getTextButton(response));
	}

	public void setAction(JSONObject response) {
			setActive(true);
			eventHandler.setAction(response);
			changeButtonText(response);
	}

	private String getTextButton(JSONObject response){
		if(response.getString("result").equals("DONE")){
			if(parent != null && turn.getActiveAction() == null){
				parent.getPhaseButton().changeButtonText("GO TO TREASURE PHASE");
				return "PLAY CARD";
			}
			return "DONE";
		}
		else{
			if(response.getString("player").equals(hand.getPlayer().getDisplayname())){
				switch(response.getString("result")){
				case "SELECT_CARD_HAND":
					if(turn.getActiveAction().getDescripton().toLowerCase().contains("trash")){
						return "TRASH CARD";
					}
					if(turn.getActiveAction().getDescripton().toLowerCase().contains("reveal")){
						return "REVEAL CARD";
					}
					if(turn.getActiveAction().getDescripton().toLowerCase().contains("choose")){
						return "GET CARD";
					}
					return "DISCARD CARD";

				case "SELECT_CARD_BOARD":
					return "GET CARD";
				case "REVEAL":
					return "SHOW CARDS";
				}
			}
			return "NEXT PLAYER";
		}
	}

	private void createButton(){
			button = new Button();
			button.setPrefHeight(36);
			button.setPadding(new Insets(0,10,0,10));
			button.setId("gameButton");
			button.setText("PLAY CARD");
			setActive(false);
			eventHandler = new ActionButtonEventHandler(this, turn);
			button.setOnAction(eventHandler);
	}
}
