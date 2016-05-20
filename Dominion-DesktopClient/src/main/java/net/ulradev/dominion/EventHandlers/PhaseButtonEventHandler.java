package net.ulradev.dominion.EventHandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import net.ultradev.dominion.DominionGUIMain;
import net.ultradev.dominion.PlayerConfirm;
import net.ultradev.dominion.game.Turn;
import net.ultradev.dominion.gameGUI.*;

public class PhaseButtonEventHandler implements EventHandler<ActionEvent>{
	private CustomButton parent;


	public PhaseButtonEventHandler(CustomButton parent){
		this.parent = parent;
	}
	@Override
    public void handle(ActionEvent event) {
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
    			parent.getParent().getPlayButton().setActive(false);
    			break;
    		case "CLEANUP":
    			goToNextPlayer(game);
    			break;
    	}
    }

	private void checkCardsPhase(){
		GUICard card= parent.getParent().getParent().getHand().getSelectedCard();
		Turn turn = parent.getParent().getParent().getTurn();
		CustomButton playbtn = parent.getParent().getPlayButton();
		if(card != null){
			playbtn.setActive(turn.canPlay(turn.getPhase(), card.getTitle()));
		}
		else{
			playbtn.setActive(false);
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
