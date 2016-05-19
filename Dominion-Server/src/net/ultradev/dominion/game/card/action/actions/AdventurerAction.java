package net.ultradev.dominion.game.card.action.actions;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;
import net.ultradev.dominion.game.Turn;
import net.ultradev.dominion.game.card.Card;
import net.ultradev.dominion.game.card.Card.CardType;
import net.ultradev.dominion.game.card.action.Action;
import net.ultradev.dominion.game.card.action.ActionResult;
import net.ultradev.dominion.game.player.Player;

public class AdventurerAction extends Action {

	public AdventurerAction(String identifier, String description, ActionTarget target) {
		super(identifier, description, target);
	}

	@Override
	public JSONObject play(Turn turn) {
		Player p = turn.getPlayer();
		
		List<Card> toReveal = new ArrayList<>();
		List<Card> treasures = new ArrayList<>();
		
		checkDeck(p, toReveal, treasures);
		
		// If there's not enough in the deck, go to the discard pile and do the same thing
		if(treasures.size() < 2) {
			p.transferDiscardToDeck();
			checkDeck(p, toReveal, treasures);
		}
		
		for(Card treasure : treasures) {
			p.getHand().add(treasure);
		}
		
		return new JSONObject()
				.accumulate("response", "OK")
				.accumulate("result", ActionResult.DONE)
				.accumulate("reveal", toReveal.stream().map(Card::getAsJson));
	}
	
	public void checkDeck(Player p, List<Card> toReveal, List<Card> treasures) {
		// Looking for treasures :))
		for(Card card : p.getDeck()) {
			if(treasures.size() < 2) {
				if(card.getType().equals(CardType.TREASURE)) {
					treasures.add(card);
				} else {
					toReveal.add(card);
				}
			}
		}
		int amount = toReveal.size() + treasures.size();
		// Remove the cards to reveal from the deck
		for(int i = 0; i < amount; i++) {
			p.getDeck().remove(0);
		}
	}

}
