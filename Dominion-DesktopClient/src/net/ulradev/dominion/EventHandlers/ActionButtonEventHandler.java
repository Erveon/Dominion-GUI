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
import net.ultradev.dominion.game.player.Player.Pile;
import net.ultradev.dominion.gameGUI.CardSet;
import net.ultradev.dominion.gameGUI.GUIGame;
import net.ultradev.dominion.gameGUI.Hand;
import net.ultradev.dominion.specialScreens.PlayerConfirm;
import net.ultradev.dominion.specialScreens.RevealedCards;

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
		//System.out.println(turn.getPlayer().getPile(Pile.HAND).size());
		if(turn.getPhase().toString().equals("ACTION")){
				doAction();
			System.out.println("(ActionEventHandler) Response: " + response);
			parent.changeButtonText(response);

		}
		if(turn.getPhase().toString().equals("BUY")){
			doBuyPhaseActions();
		}
	}

	public void setAction(JSONObject response){
		this.response = response;
	}

	private void doAction(){
		System.out.println("before action: " + response);
		if(turn.getActiveAction() == null ){
			playCard();
		}else{
			switch(response.getString("result")){
				case "DONE":
					if(parent.getParent() == null){
						PlayerConfirm playerConfirm = new PlayerConfirm(turn.getGame());
			    		DominionGUIMain.setRoot(playerConfirm.getRoot());
					}
					else{
						turn.getActiveAction().finish(turn);
						parent.getParent().getParent().getTopMenu().reloadCounters();
						parent.getHand().reloadCards(false);
						parent.getParent().getParent().getCardSet().loadTrashAndDeck();
					}
		    		break;
				case "REVEAL":
					RevealedCards tmpScreen = new RevealedCards(response, turn);
					DominionGUIMain.setRoot(tmpScreen.getRoot());
					break;
				default:
					if(response.getString("player").equals(parent.getHand().getPlayer().getDisplayname())){
						Hand hand = parent.getHand();
						if(parent.getParent() != null){
							CardSet cardset = parent.getParent().getParent().getCardSet();
							selectCard(cardset,hand);
						}
						else{
							selectCardHand(parent.getHand());
						}

					}else{
						if(parent.getParent() != null)
						{
							parent.getParent().getParent().getCardViewer().close();
						}
						goToNextPlayer(response.getString("player"));
					}
				}
			}
		}

	public void playCard(){
		//Kaart spelen en response verzenden naar button

		GUICard selectedCard = parent.getHand().getSelectedCard();
			JSONObject newResponse = turn.playCard(selectedCard.getTitle());
			if(newResponse.getString("response").equals("OK")){
				setAction(newResponse);
				selectedCard.removeCardGUI();
				parent.getParent().getParent().getCardSet().loadTrashAndDeck();
				parent.getHand().reloadCards(false);
				if(!response.getString("result").equals("DONE")){
					parent.getParent().getParent().getPlayerbalk().getActionButton().setAction(response);
					parent.getParent().getParent().getPlayerbalk().getPhaseButton().changeButtonText("STOP ACTION");
				}
				if(response.getString("result").equals("SELECT_CARD_BOARD")){
					parent.getParent().getParent().getCardSet().setCost(response.getInt("cost"));
				}
			}

	}

	public void buyCard(){
		try{
			KingdomCard selectedCard = parent.getParent().getParent().getCardSet().getSelectedKingdomCard();
			String response = turn.buyCard(selectedCard.getTitle()).getString("result");
			if(response.equals("BOUGHT"))		{
				selectedCard.decreaseAmount();
				parent.getParent().getParent().getTopMenu().reloadCounters();
				parent.setActive(false);
			}}
			catch(Exception e){
			}
	}

	private void doBuyPhaseActions(){
		GUIGame game = parent.getParent().getParent();
		if(game.getHand().getSelectedCard() != null){
			//Om treasures te spelen
			playCard();
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
			JSONObject newresponse = turn.getActiveAction().selectCard(turn, cardHand.getSourceCard());
			if(newresponse.get("response").equals("OK") ){
				hand.getSelectedCard().removeCardGUI();
				setAction(newresponse);
			}

		}
	}

	private void selectKingdomCard(CardSet cardset){
		KingdomCard kingdomCard = cardset.getSelectedKingdomCard();
		if(kingdomCard != null ){
			JSONObject newresponse  = turn.getActiveAction().selectCard(turn, kingdomCard.getSourceCard());
			if(response.get("response").equals("OK") ){
				cardset.getSelectedKingdomCard().decreaseAmount();
				cardset.setCost(turn.getBuypower());
				setAction(newresponse);
			}
		}
	}

	private void goToNextPlayer(String targetPlayer){
		if(!targetPlayer.equals(turn.getPlayer().getDisplayname())){
			PlayerConfirm playerConfirm = new PlayerConfirm(turn.getGame(),response);
	    	DominionGUIMain.setRoot(playerConfirm.getRoot());
		}else{
			PlayerConfirm playerConfirm = new PlayerConfirm(turn.getGame());
	    	DominionGUIMain.setRoot(playerConfirm.getRoot());
	    }
	}



}