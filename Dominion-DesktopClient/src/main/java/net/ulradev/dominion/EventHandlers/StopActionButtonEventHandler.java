package net.ulradev.dominion.EventHandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;import net.sf.json.JSONObject;
import net.ultradev.dominion.game.card.action.Action;
import net.ultradev.dominion.gameGUI.CustomButton;
import net.ultradev.dominion.gameGUI.GUIGame;

public class StopActionButtonEventHandler implements EventHandler<ActionEvent>  {
	private CustomButton parent;

	public StopActionButtonEventHandler(CustomButton parent){
		this.parent = parent;
	}

	@Override
	public void handle(ActionEvent event){
		Action action = parent.getParent().getParent().getTurn().getActiveAction();
		if(action != null){
			parent.getParent().getParent().getTurn().stopAction();
			parent.getParent().getActionButton().setAction(new JSONObject().accumulate("response", "OK").accumulate("result", "DONE"));
			parent.setActive(false);
			parent.getParent().getActionButton().setActive(false);
		}

	}
}
