package net.ultradev.dominion.gameGUI;

import java.util.*;

import javafx.geometry.Pos;
import javafx.scene.layout.*;
import net.ultradev.domininion.GUIUtils.Carousel;
import net.ultradev.dominion.cardsGUI.MiniCard;

public class PlayZone {
	private HBox playzone;
	private Carousel c ;

	public PlayZone(ArrayList<MiniCard> cardsPlayed){
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
		playzone.setPrefHeight(300);
		//playzone.setPrefHeight(150);
		//playzone.setFillHeight(true);
		playzone.setSpacing(10);

	}





}
