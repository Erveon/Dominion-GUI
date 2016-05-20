package net.ulradev.dominion.EventHandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import net.sf.json.JSONObject;
import net.ultradev.dominion.DominionGUIMain;
import net.ultradev.dominion.PlayerConfirm;
import net.ultradev.dominion.game.Turn;
import net.ultradev.dominion.game.card.Card;
import net.ultradev.dominion.game.card.action.Action;
import net.ultradev.dominion.gameGUI.CardSet;
import net.ultradev.dominion.gameGUI.CustomButton;
import net.ultradev.dominion.gameGUI.GUICard;
import net.ultradev.dominion.gameGUI.Hand;
import net.ultradev.dominion.gameGUI.KingdomCard;

public class ActionButtonEventHandler implements EventHandler<ActionEvent>{
	private boolean actionActive;
	private JSONObject response;
	private Action action;
	private Turn turn;


	private CustomButton parent;

	public ActionButtonEventHandler(CustomButton parent , Turn turn){
		this.parent = parent;
		this.turn = turn;
	}

	@Override
	public void handle(ActionEvent event){
		Hand hand = parent.getHand();

		//TODO: Militia invoeren + kader kingdomkaarten aanpassen
		if(checkInPlayerBalk()){
			//Nog opvangen als er geen karten geselecteerd zijn
			CardSet cardset = parent.getParent().getParent().getCardSet();
			if(response.getString("result").equals("DONE")){
				parent.setActive(false);
			}
			else{
				if(response.getString("player").equals(turn.getPlayer().getDisplayname())){
					selectCard(cardset,hand);
				}else{
					parent.getParent().getParent().getCardViewer().close();
					goToNextPlayer();
				}
			}
		}
		else{
			if(!response.getString("result").equals("DONE")){
				selectCardHand(hand);
			}else{
				parent.getButton().setText("NEXT PLAYER");
				goToNextPlayer();
			}
		}

		parent.changeButtonText(response);
		System.out.println("(ActionButtonEventHandler) response: "+ response);
	}

	private boolean checkInPlayerBalk(){
		return (parent.getParent() != null);
	}


	private void selectCard(CardSet cardset, Hand hand){
		selectKingdomCard(cardset);
		selectCardHand(hand);
	}

	private void selectCardHand(Hand hand){
		GUICard handCard = hand.getSelectedCard();
		if(handCard != null){
			response = action.selectCard(turn, handCard.getSourceCard());
			if(response.get("response").equals("OK") ){
				hand.getSelectedCard().removeCardGUI();
			}
		}
	}

	private void selectKingdomCard(CardSet cardset){
		KingdomCard kingdomCard = cardset.getSelectedKingdomCard();
		if(kingdomCard != null ){
			response = action.selectCard(turn, kingdomCard.getSourceCard());
			if(response.get("response").equals("OK") ){
				cardset.getSelectedKingdomCard().decreaseAmount();
			}
		}
	}

	private void goToNextPlayer(){
		if(response.getString("result").equals("DONE")){
			PlayerConfirm playerConfirm = new PlayerConfirm(turn.getGame(),true);
	    	DominionGUIMain.setRoot(playerConfirm.getRoot());
		}
		else{
			PlayerConfirm playerConfirm = new PlayerConfirm(turn.getGame(),response);
	    	DominionGUIMain.setRoot(playerConfirm.getRoot());
		}

	}

	public void setAction(JSONObject response){
		this.response = response;
		action = turn.getActiveAction();
	}

}