package net.ulradev.dominion.EventHandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import net.sf.json.JSONObject;
import net.ultradev.dominion.DominionGUIMain;
import net.ultradev.dominion.Buttons.ActionButton;
import net.ultradev.dominion.Buttons.PhaseButton;
import net.ultradev.dominion.cardsGUI.GUICard;
import net.ultradev.dominion.game.Turn;
import net.ultradev.dominion.game.card.action.Action;
import net.ultradev.dominion.gameGUI.*;
import net.ultradev.dominion.specialScreens.PlayerConfirm;

public class PhaseButtonEventHandler implements EventHandler<ActionEvent>{
	private PhaseButton parent;

	public PhaseButtonEventHandler(PhaseButton phaseButton){
		this.parent = phaseButton;
	}
	@Override
    public void handle(ActionEvent event) {
		if(parent.getParent().getParent().getTurn().getActiveAction() == null){
			changePhase();
		}
		else{
			stopAction();
		}
    }

	private void changePhase(){
		GUIGame game = parent.getParent().getParent();
		Turn turn = game.getTurn();
    	switch(turn.getPhase().toString()){
    		case "ACTION":
    			turn.endPhase();
    			changePhaseGUI(0,"GO TO CLEAN-UP PHASE");
    			checkCardsPhase();
    			break;
    		case "BUY":
    			turn.endPhase();
    			changePhaseGUI(1,"END TURN");
    			loadHand(game);
    			parent.getParent().getActionButton().setActive(false);
    			break;
    		case "CLEANUP":
    			goToNextPlayer(game);
    			break;
    	}
	}

	private void stopAction(){
		Action action = parent.getParent().getParent().getTurn().getActiveAction();
		if(action != null){
			parent.getParent().getParent().getTurn().stopAction();
			parent.getParent().getActionButton().setAction(new JSONObject().accumulate("response", "OK").accumulate("result", "DONE"));
			parent.getParent().getActionButton().setActive(false);
			parent.changeButtonText("GO TO TREASURE PHASE");
		}
	}

	private void checkCardsPhase(){
		GUICard card= parent.getParent().getParent().getHand().getSelectedCard();
		Turn turn = parent.getParent().getParent().getTurn();
		ActionButton actionBtn = parent.getParent().getActionButton();
		if(card != null){
			actionBtn.setActive(turn.canPlay(turn.getPhase(), card.getTitle()));
		}
		else{
			actionBtn.setActive(false);
		}

	}

	private void changePhaseGUI(int index, String text){
		parent.getParent().changeCircle(index);
		parent.getButton().setText(text);
	}

	private void loadHand(GUIGame game){
		game.getHand().reloadCards(true);
		game.loadCardsPlayed();
		game.getCardSet().loadRows();
	}

	private void goToNextPlayer(GUIGame game){
		game.getCardViewer().close();
    	PlayerConfirm playerConfirm = new PlayerConfirm(game.getTurn().getGame(),false);
    	DominionGUIMain.setRoot(playerConfirm.getRoot());
	}
}
