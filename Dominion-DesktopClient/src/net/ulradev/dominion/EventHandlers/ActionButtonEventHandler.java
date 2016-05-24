package net.ulradev.dominion.EventHandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import net.sf.json.JSONObject;
import net.ultradev.dominion.DominionGUIMain;
import net.ultradev.dominion.Buttons.ActionButton;
import net.ultradev.dominion.cardsGUI.GUICard;
import net.ultradev.dominion.cardsGUI.KingdomCard;
import net.ultradev.dominion.game.Turn;
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
		if(turn.getPhase().toString().equals("ACTION")){
			doAction();
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
		if(turn.getActiveAction() == null ){
			playCard();
		}else{
			switch(response.getString("result")){
				case "DONE":
					if(inSelectionScreen()){
						PlayerConfirm playerConfirm = new PlayerConfirm(turn.getGame());
			    		DominionGUIMain.setRoot(playerConfirm.getRoot());
					}
					else{
						turn.getActiveAction().finish(turn);
						reloadGame();
					}
		    		break;
				case "REVEAL":
					closeCardViewer();
					RevealedCards tmpScreen = new RevealedCards(response, turn);
					DominionGUIMain.setRoot(tmpScreen.getRoot());
					break;
				case "SELECT_CARD_BOARD":
					CardSet cardset = parent.getParent().getParent().getCardSet();
					selectKingdomCard(cardset);
					break;
				default:
					if(response.getString("player").equals(parent.getHand().getPlayer().getDisplayname())){
						selectCardHand(parent.getHand());
					}else{
						closeCardViewer();
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
				selectedCard.removeCardGUI();
				setAction(newResponse);
				reloadGame();
				if(!response.getString("result").equals("DONE")){
					if(response.getString("result").equals("SELECT_CARD_BOARD")){
						parent.getParent().getParent().getCardSet().setCost(response.getInt("cost"));
					}
					parent.changeButtonText(newResponse);
					if(newResponse.getInt("max") == 0){
						parent.getParent().getParent().getPlayerbalk().getPhaseButton().mayStop(true);
						parent.getParent().getParent().getPlayerbalk().getPhaseButton().changeButtonText("STOP ACTION");
					}
					else{
						parent.getParent().getParent().getPlayerbalk().getPhaseButton().mayStop(false);
					}

				}
				else{
					parent.getParent().getParent().getCardSet().setCost(turn.getBuypower());
				}
			}

	}

	public void buyCard(){
		try{
			KingdomCard selectedCard = parent.getParent().getParent().getCardSet().getSelectedKingdomCard();
			String response = turn.buyCard(selectedCard.getTitle()).getString("result");
			if(response.equals("BOUGHT"))		{
				selectedCard.decreaseAmount();
				reloadGame();
			}}
			catch(Exception e){
			}
	}

	private void doBuyPhaseActions(){
		GUIGame game = parent.getParent().getParent();
		if(game.getHand().getSelectedCard() != null){
			playCard();
		}
		if(game.getCardSet().getSelectedKingdomCard() != null){
			buyCard();
		}
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

	private void reloadGame(){
		parent.getParent().getParent().getTopMenu().reloadCounters();
		parent.getHand().reloadCards(false);
		parent.getParent().getParent().getCardSet().loadTrashAndDeck();
	}

	private boolean inSelectionScreen(){
		return parent.getParent() == null;
	}

	private void closeCardViewer(){
		if(!inSelectionScreen())
		{
			parent.getParent().getParent().getCardViewer().close();
		}
	}

}