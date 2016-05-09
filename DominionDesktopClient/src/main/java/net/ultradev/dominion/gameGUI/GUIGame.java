package net.ultradev.dominion.gameGUI;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import net.ultradev.dominion.game.Turn;
import net.ultradev.dominion.game.local.LocalGame;
import net.ultradev.dominion.game.player.Player;

public class GUIGame {
	private BorderPane root;
	private Turn turn;
	private ArrayList<miniCard> cardsPlayed;
	private PlayZone playzone;
	private Hand hand;
	private GameTopMenu topMenu;

	private GUtils utils = new GUtils();

	public GUIGame(Turn turn){
		cardsPlayed = new ArrayList<miniCard>();
		this.turn = turn;

		createGameGUI();
	}

	public BorderPane getRoot(){
		return root;
	}

	public ArrayList<miniCard>  getListCardsPlayed(){
		return cardsPlayed;
	}

	public PlayZone getPlayZone(){
		return playzone;
	}

	public Hand getHand(){
		return hand;
	}

	public Turn getTurn(){
		return turn;
	}
	public GameTopMenu getTopMenu(){
		return topMenu;
	}


	private void createGameGUI(){
		root = new BorderPane();
		root.setId("Game");
		root.setPrefWidth(1280);
		topMenu = new GameTopMenu(turn);

		root.setTop(topMenu.getMenu());
		HBox center = new HBox();
		center.setId("center");
		center.setPadding(new Insets(10,0,0,0));
		VBox gameZone = createGameZone();
		center.getChildren().add(gameZone);
		center.setAlignment(Pos.TOP_CENTER);
		root.setCenter(center);

		hand = new Hand(turn.getPlayer().getHand(),this);

		root.setBottom(hand.getHand());
	}

	private HBox createMenu(){
		HBox container = createGameMenuContainer();
		HBox titleBox = createTitleBox();
		HBox counterBox = createCounterBox();
		//TODO dropdown menu maken met https://docs.oracle.com/javafx/2/ui_controls/menu_controls.htm

		HBox menuBox = new HBox();
		menuBox.setAlignment(Pos.CENTER);
		Text placeholder = new Text("Menu");

		menuBox.getChildren().add(placeholder);
		menuBox.setPrefWidth(100);


		container.getChildren().addAll(titleBox, counterBox, menuBox);
		return container;
	}

	private HBox createGameMenuContainer(){
		HBox menuContainer = utils.createCenterHBox("GameMenu");
		menuContainer.setFillHeight(true);
		menuContainer.setPrefHeight(60);
		return menuContainer;
	}

	private HBox createTitleBox(){
		HBox titleBox = new HBox();
		titleBox.setAlignment(Pos.CENTER_LEFT);

		Text title = new Text("Dominion");
		title.setId("Title");

		titleBox.getChildren().add(title);
		titleBox.setPrefWidth(100);
		return titleBox;

	}

	private HBox createCounterBox(){
		HBox counterBox = utils.createCenterHBox("");
		counterBox.setPrefWidth(900);

		VBox actionCounter = createCounter("ACTION",turn.getActions());
		VBox buyCounter = createCounter("BUYS",turn.getBuys());
		VBox coinCounter = createCounter("COINS",turn.getBuypower());

		counterBox.getChildren().addAll(actionCounter,buyCounter,coinCounter);
		return counterBox;
	}

	private VBox createCounter(String text, int number){
		VBox counter = new VBox();
		counter.setAlignment(Pos.CENTER);
		counter.setPrefWidth(100);
		counter.setFillWidth(true);

		Text label = new Text();
		label.setText(text);
		label.setId("label");

		Text count = new Text();
		count.setText(String.valueOf(number));
		count.setId("count");

		counter.getChildren().addAll(label,count);
		return counter;
	}

	private VBox createGameZone(){
		VBox container = new VBox();
		container.setSpacing(20);

		CardSet cardSet = new CardSet(turn);

		playzone = new PlayZone(cardsPlayed);

		PlayerBalk playerbalk = new PlayerBalk(turn,this);


		container.getChildren().addAll(cardSet.getCardset(), playzone.getPlayZone(),playerbalk.getPlayerBalk());
		return container;

	}

	private void reloadCounters(){

	}

}
