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
	private List<Card> listCardsInHand;
	private ArrayList<GUICard> cards;
	private Carousel c ;
	private GUIGame parent;

	private GUtils utils = new GUtils();

	public Hand(List<Card> listCardsInHand, GUIGame parent){
		this.listCardsInHand = listCardsInHand;
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

	public Carousel getCarousel(){
		return c;
	}

	private void createHand(){
		hand = utils.createCenterHBox("hand");
		hand.setPrefSize(1280, 330);
		hand.setPadding(new Insets(10,0,10,0));
		hand.setSpacing(10);

		cards = new ArrayList<GUICard>();

		loadCards();



		c = new Carousel();
		c.setCarousel(this.getHand(), this.getCards());
	}

	public void loadCards(){
		getCards().clear();
		for(int i=0;i<listCardsInHand.size();i++){
			GUICard card = new GUICard(listCardsInHand.get(i),this);
			cards.add(card);
		}
	}




}
