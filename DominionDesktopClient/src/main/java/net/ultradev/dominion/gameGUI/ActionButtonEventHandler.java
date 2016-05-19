package net.ultradev.dominion.gameGUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import net.sf.json.JSONObject;
import net.ultradev.dominion.game.card.Card;
import net.ultradev.dominion.game.card.CardManager;
import net.ultradev.dominion.game.card.action.Action;
import net.ultradev.dominion.game.card.action.actions.*;

public class ActionButtonEventHandler implements EventHandler<ActionEvent>{
	private boolean actionActive;
	private PlayerBalk parent;
	private Action action;

	public ActionButtonEventHandler(PlayerBalk parent){
		setActive(false);
		this.parent = parent;
	}

	public void setAction(Action action){
		this.action = action;
	}

	@Override
	public void handle(ActionEvent event){
		if(actionActive){
			JSONObject response = new JSONObject().accumulate("response", "invalid").accumulate("result", "running");

			if(action instanceof RemoveCardAction){
				response = ((RemoveCardAction) action).selectCard(parent.getParent().getTurn(), parent.getParent().getHand().getActiveGCard().getSourceCard());

				//System.out.println(response);

				if(response.getString("response").equals("OK")){
					parent.getParent().getHand().getActiveGCard().removeCardGUI();
					if(response.getString("result").equals("DONE")){
						actionActive = false;
					}
					if(response.getString("result").equals("SELECT_CARD_BOARD")){
						action = action.getCallbacks().get(0);
						parent.getDiscardButton().getButton().setText("SELECT CARD");
					}

				}
			}
			if(action instanceof GainCardAction){
				if(parent.getParent().getCardSet().getSelectedKingdomCard() != null  ){
					CardManager cm = parent.getParent().getTurn().getGame().getGameServer().getCardManager();
					Card card = cm.get(parent.getParent().getCardSet().getSelectedKingdomCard().getTitle());
					response = ((GainCardAction) action).selectCard(parent.getParent().getTurn(), card);
				}
				//System.out.print("GAIN Card: ");
				//System.out.println(response);

			}


		}

		parent.getDiscardButton().setActive(actionActive);
		parent.getParent().getCardSet().loadTrashAndDeck();
	}

	public void setActive(boolean b){
		actionActive = b;
	}
}
