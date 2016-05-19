package net.ultradev.dominion.gameGUI;

import java.util.*;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import net.ultradev.dominion.game.card.Card;


public class Hand {
	private HBox hand;

	private ArrayList<GUICard> cards;
	private Carousel c ;
	private GUIGame parent;
	private GUICard activeGCard;

	private Card lastCardPlayed;
	private GUtils utils = new GUtils();

	public Hand( GUIGame parent){
		this.parent = parent;
		createHand();
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

	public void removeBorder(){
		activeGCard.getCard().setStyle("-fx-border: none");
		activeGCard = null;
	}

	public void setActiveGCard(GUICard card){
		activeGCard = card;
		activeGCard.getCard().setStyle("-fx-border-color: white; -fx-border-width: 4");
		for(int i = 0; i< getCards().size();i++){
			if(!getCards().get(i).equals(activeGCard)){
				getCards().get(i).getCard().setStyle("-fx-border: none");
			}
		}
		getParent().getPlayerbalk().getPlayButton().setActive(getParent().getTurn().canPlay(getParent().getTurn().getPhase(), activeGCard.getTitle()));
	}
	public GUICard getActiveGCard(){
		return activeGCard;
	}

	public Carousel getCarousel(){
		return c;
	}

	private void loadCards(){
		List<Card> listCardsInHand = parent.getTurn().getPlayer().getHand();

		for(int i=0;i<listCardsInHand.size();i++){
			GUICard card = new GUICard(listCardsInHand.get(i),this);
			cards.add(card);
		}
		c = new Carousel();
		c.setCarousel(this.getHand(), this.getCards());

	}

	public void reloadCards(boolean fullReload){
		if(fullReload){
			getCards().clear();
		}
		if(getCards().size() < parent.getTurn().getPlayer().getHand().size())
		{	//TODO TESTEN
			for(int i = getCards().size(); i <  parent.getTurn().getPlayer().getHand().size();i++){
				GUICard card = new GUICard(parent.getTurn().getPlayer().getHand().get(i),this);
				cards.add(card);
			}
		}
		c.setCarousel(this.getHand(), this.getCards());
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
