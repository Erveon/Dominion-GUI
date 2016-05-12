package net.ultradev.dominion.gameGUI;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import net.sf.json.JSONObject;
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
	private PlayerBalk playerbalk;
	private GUtils utils = new GUtils();
	private CardSet cardSet;


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

	public PlayerBalk getPlayerbalk(){
		return playerbalk;
	}
	public CardSet getCardSet(){
		return cardSet;
	}

	public void loadCardsPlayed(){
		cardsPlayed.clear();
		List<JSONObject> JSONCardsPlayed = turn.getGame().getBoard().getPlayedCards();
		for(int i = 0; i< JSONCardsPlayed.size(); i++){
			JSONObject card = JSONCardsPlayed.get(i);
			miniCard m = new miniCard(card);
			getListCardsPlayed().add(m);
		}
		getPlayZone().getCarousel().setCarouselMini(getPlayZone().getPlayZone(),getListCardsPlayed());
	}

	private void createGameGUI(){
		root = new BorderPane();
		root.setId("Game");
		root.setPrefWidth(1280);
		topMenu = new GameTopMenu(turn, this);

		root.setTop(topMenu.getMenu());
		HBox center = new HBox();
		center.setId("center");
		center.setPadding(new Insets(10,0,0,0));
		VBox gameZone = createGameZone();
		center.getChildren().add(gameZone);
		center.setAlignment(Pos.TOP_CENTER);
		root.setCenter(center);

		hand = new Hand(this);

		root.setBottom(hand.getHand());
	}

	private VBox createGameZone(){
		VBox container = new VBox();
		container.setSpacing(20);

		cardSet = new CardSet(turn, this);

		playzone = new PlayZone(cardsPlayed);

		playerbalk = new PlayerBalk(turn,this);


		container.getChildren().addAll(cardSet.getCardset(), playzone.getPlayZone(),playerbalk.getPlayerBalk());
		return container;

	}



}
