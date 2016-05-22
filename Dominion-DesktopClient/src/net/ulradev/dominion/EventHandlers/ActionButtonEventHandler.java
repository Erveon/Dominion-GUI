package net.ulradev.dominion.EventHandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import net.sf.json.JSONObject;
import net.ultradev.dominion.DominionGUIMain;
import net.ultradev.dominion.Buttons.ActionButton;
import net.ultradev.dominion.cardsGUI.GUICard;
import net.ultradev.dominion.cardsGUI.KingdomCard;
import net.ultradev.dominion.game.Turn;
import net.ultradev.dominion.game.card.Card;
import net.ultradev.dominion.game.card.action.Action;
import net.ultradev.dominion.gameGUI.CardSet;
import net.ultradev.dominion.gameGUI.GUIGame;
import net.ultradev.dominion.gameGUI.Hand;
import net.ultradev.dominion.specialScreens.PlayerConfirm;

public class ActionButtonEventHandler implements EventHandler<ActionEvent>{
	private Turn turn;
	private JSONObject response;

	private ActionButton parent;

	public ActionButtonEventHandler(ActionButton parent , Turn turn){
		this.parent = parent;
		this.turn = turn;
	}

	@Override
	public void handle(ActionEvent event){
		//Hand hand = parent.getHand();

		if(turn.getPhase().toString().equals("ACTION")){
			if(parent.getParent() != null){
				doAction();
			}
			else{
				selectionAction();
			}
			parent.changeButtonText(response);
		}
		if(turn.getPhase().toString().equals("BUY")){
			buyCard();
		}


	}

	public void setAction(JSONObject response){
		this.response = response;
	}

	private void doAction(){
			if(response.getString("result").equals("DONE")){
				parent.getHand().getSelectedCard().playCard();
			}
			if(response.getString("player").equals(parent.getHand().getPlayer().getDisplayname())){
				Hand hand = parent.getHand();
				CardSet cardset = parent.getParent().getParent().getCardSet();
				selectCard(cardset,hand);

			}else{
				parent.getParent().getParent().getCardViewer().close();
				goToNextPlayer();
			}
	}

	private void selectionAction(){

		if(response.getString("result").equals("DONE")){
			PlayerConfirm playerConfirm = new PlayerConfirm(turn.getGame(),true);
	    	DominionGUIMain.setRoot(playerConfirm.getRoot());
		}else
		{
			if(response.getString("player").equals(parent.getHand().getPlayer().getDisplayname())){
				selectCardHand(parent.getHand());
			}
			else{
				if(response.getString("result").equals("DONE")){
					PlayerConfirm playerConfirm = new PlayerConfirm(turn.getGame(),response);
					DominionGUIMain.setRoot(playerConfirm.getRoot());
				}else
				{
					goToNextPlayer();
				}
			}
		}
	}

	private void buyCard(){
		GUIGame game = parent.getParent().getParent();
		if(game.getHand().getSelectedCard() != null){
			//Om treasures te spelen
			game.getHand().getSelectedCard().playCard();
		}

		if(game.getCardSet().getSelectedKingdomCard() != null){
			game.getCardSet().getSelectedKingdomCard().buyCard();
		}
	}

	private void selectCard(CardSet cardset, Hand hand){
		selectKingdomCard(cardset);
		selectCardHand(hand);
	}

	private void selectCardHand(Hand hand){
		GUICard cardHand = hand.getSelectedCard();
		if(cardHand != null){
			JSONObject response = turn.getActiveAction().selectCard(turn, cardHand.getSourceCard());
			if(response.get("response").equals("OK") ){
				hand.getSelectedCard().removeCardGUI();
				setAction(response);
			}
		}
	}

	private void selectKingdomCard(CardSet cardset){
		KingdomCard kingdomCard = cardset.getSelectedKingdomCard();
		if(kingdomCard != null ){
			JSONObject response = turn.getActiveAction().selectCard(turn, kingdomCard.getSourceCard());
			if(response.get("response").equals("OK") ){
				cardset.getSelectedKingdomCard().decreaseAmount();
			}
		}
	}

	private void goToNextPlayer(){
			PlayerConfirm playerConfirm = new PlayerConfirm(turn.getGame(),response);
	    	DominionGUIMain.setRoot(playerConfirm.getRoot());
	}



}