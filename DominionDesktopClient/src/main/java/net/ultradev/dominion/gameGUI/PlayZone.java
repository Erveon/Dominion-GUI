package net.ultradev.dominion.gameGUI;

import java.util.*;

import javafx.geometry.Pos;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class PlayZone {
	private HBox playzone;
	private Carousel c ;
	private ArrayList<miniCard> playedCards;

	public PlayZone(ArrayList<miniCard> cardsPlayed){
		playedCards = cardsPlayed;
		c = new Carousel();
		createPlayZone();
	}

	public HBox getPlayZone(){
		return playzone;
	}
	public Carousel getCarousel(){
		return c;
	}
	private void createPlayZone(){
		playzone = new HBox();
		playzone.setAlignment(Pos.CENTER);
		playzone.setPrefHeight(150);
		playzone.setFillHeight(true);
		playzone.setSpacing(10);


		/*if(!playedCards.isEmpty()){
			c = new Carousel();
			c.setCarouselMini(playzone,playedCards);
		}*/

	}





}
