package net.ultradev.dominion.gameGUI;

import java.util.*;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import net.ultradev.dominion.Buttons.ActionButton;
import net.ultradev.dominion.GUIUtils.Carousel;
import net.ultradev.dominion.GUIUtils.GUtils;
import net.ultradev.dominion.cardsGUI.GUICard;
import net.ultradev.dominion.game.card.Card;
import net.ultradev.dominion.game.player.Player;
import net.ultradev.dominion.game.player.Player.Pile;


public class Hand {
	private HBox hand;

	private ArrayList<GUICard> cards;
	private Carousel c ;
	private GUIGame parent;
	private GUICard selectedCard;

	private Card lastCardPlayed;
	private GUtils utils = new GUtils();

	private Player player;
	private boolean selectionScreen;

	private ActionButton actionButton;


	public Hand(GUIGame parent){
		this.parent = parent;
		player = parent.getPlayer();
		selectionScreen = false;
		actionButton = parent.getPlayerbalk().getActionButton();
		createHand();
	}

	public Hand(Player player, ActionButton actionBtn){
		this.player = player;
		selectionScreen = true;
		actionButton = actionBtn;
		createHand();
	}

	public ActionButton getActionButton(){
		return actionButton;
	}

	public boolean getIfSelectionScreen(){
		return selectionScreen;
	}

	public HBox getHand(){
		return hand;
	}

	public GUIGame getParent(){
		return parent;
	}

	public ArrayList<GUICard>  getCards(){
		return cards;
	}

	public void setLastCardPlayed(Card card){
		lastCardPlayed = card;
	}

	public Card getLastPlayedCard(){
		return lastCardPlayed;
	}
	public Player getPlayer(){
		return player;
	}

	public void removeBorder(){
		selectedCard.getCard().setStyle("-fx-border: none");
		selectedCard = null;
	}

	public void setActiveGCard(GUICard card){
		selectedCard = card;
		if(selectedCard != null){
			selectedCard.getCard().setStyle("-fx-border-color: white; -fx-border-width: 4");
			for(int i = 0; i< getCards().size();i++){
				if(!getCards().get(i).equals(selectedCard)){
					getCards().get(i).getCard().setStyle("-fx-border: none");
				}
		}
		if(player.getGame().getTurn().getActiveAction() == null)
		{
			getParent().getPlayerbalk().getActionButton().setActive(getParent().getTurn().canPlay(getParent().getTurn().getPhase(), selectedCard.getTitle()));
		}
		}
	}
	public GUICard getSelectedCard(){
		return selectedCard;
	}

	public Carousel getCarousel(){
		return c;
	}

	private void loadCards(){
		List<Card> listCardsInHand = player.getPile(Pile.HAND);
		addCards(0,listCardsInHand.size());
		c = new Carousel();
		c.setCarousel(this.getHand(), this.getCards());

	}

	public void reloadCards(boolean fullReload){
		if(fullReload){
			getCards().clear();
		}
		if(getCards().size() < player.getPile(Pile.HAND).size())
		{
			addCards(getCards().size(), player.getPile(Pile.HAND).size());
		}
		c.setCarousel(this.getHand(), this.getCards());

	}

	private void addCards(int start, int finish){
		for(int i = start; i < finish;i++){
			GUICard card = new GUICard(player.getPile(Pile.HAND).get(i),this);
			cards.add(card);
		}
	}

	private void createHand(){
		hand = utils.createCenterHBox("hand");
		hand.setPrefSize(1280, 330);
		hand.setPadding(new Insets(10,0,10,0));
		hand.setSpacing(10);

		cards = new ArrayList<GUICard>();

		loadCards();




	}






}
