package net.ulradev.dominion.EventHandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import net.ultradev.dominion.gameGUI.*;

public class PlayButtonEventHandler implements EventHandler<ActionEvent> {
	private CustomButton parent;

	public PlayButtonEventHandler(CustomButton parent){
		this.parent = parent;
	}

	@Override
	public void handle(ActionEvent event){
		try{
			GUIGame game = parent.getParent().getParent();
			if(game.getTurn().getPhase().toString().equals("ACTION"))
			{
				game.getHand().getSelectedCard().playCard();
			}
			if(game.getTurn().getPhase().toString().equals("BUY"))
			{
				if(game.getHand().getSelectedCard() != null){
					game.getHand().getSelectedCard().playCard();
				}

				if(game.getCardSet().getSelectedKingdomCard() != null){
					game.getCardSet().getSelectedKingdomCard().buyCard();;
				}
			}
		}catch(Exception e){
		}
	}
}
