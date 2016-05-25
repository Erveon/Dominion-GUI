package net.ultradev.dominion.EventHandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.ultradev.dominion.DominionGUIMain;
import net.ultradev.dominion.Buttons.ActionButton;
import net.ultradev.dominion.Buttons.PhaseButton;
import net.ultradev.dominion.cardsGUI.GUICard;
import net.ultradev.dominion.game.Turn;
import net.ultradev.dominion.game.card.action.Action;
import net.ultradev.dominion.gameGUI.*;
import net.ultradev.dominion.specialScreens.PlayerConfirm;
import net.ultradev.dominion.specialScreens.VictoryScreen;

public class PhaseButtonEventHandler implements EventHandler<ActionEvent>{
	private PhaseButton parent;
	private boolean mayStop;

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
    	  		turn.getGame().endPhase();
    			changePhaseGUI(0,"GO TO CLEAN-UP PHASE");
    			checkCardsPhase();
    			break;
    		case "BUY":
    			turn.endPhase();
    			changePhaseGUI(1,"END TURN");

    			loadGame(game);
    			parent.getParent().getActionButton().setActive(false);
    			break;
    		case "CLEANUP":
    			JSONObject response = turn.getGame().endPhase();
    			//System.out.println("(PhaseEventhandler) response: " + response);
    			try{
    			if(response.getString("result").equals("GAMEOVER")){
    				String nameWinner = response.getString("winner");
    				JSONArray spelers  = response.getJSONArray("players");
    				String points = getPoints(spelers, nameWinner);
    				VictoryScreen screen = new VictoryScreen(nameWinner,points);
    				DominionGUIMain.setRoot(screen.getRoot());
    			}
    			}catch(Exception e){
    				goToNextPlayer(game);
    			}
    			break;
    	}
	}
	private String getPoints(JSONArray lijstSpelers, String nameWinner){
		String points = "";
		for(int i = 0; i< lijstSpelers.size();i++){
			JSONObject speler = lijstSpelers.getJSONObject(i);
			if(speler.getString("displayname").equals(nameWinner)){
				points = speler.getString("victorypoints");
			}
		}
		return points;
	}
	private void stopAction(){
		Action action = parent.getParent().getParent().getTurn().getActiveAction();
		if(action != null  && mayStop){
			parent.getParent().getParent().getTurn().stopAction();
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

	private void loadGame(GUIGame game){
		game.getHand().reloadCards(true);
		game.loadCardsPlayed();
		game.getCardSet().loadRows();
	}

	private void goToNextPlayer(GUIGame game){
		game.getCardViewer().close();
    	PlayerConfirm playerConfirm = new PlayerConfirm(game.getTurn().getGame());
    	DominionGUIMain.setRoot(playerConfirm.getRoot());
	}

	public void setMayStop(boolean b){
		mayStop = b;
	}
}
