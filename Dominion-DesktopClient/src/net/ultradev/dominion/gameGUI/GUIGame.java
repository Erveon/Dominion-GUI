package net.ultradev.dominion.gameGUI;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import net.sf.json.JSONObject;
import net.ultradev.dominion.game.Turn;
import net.ultradev.dominion.game.player.Player;


public class GUIGame {
	private BorderPane root;
	private Turn turn;
	private ArrayList<miniCard> cardsPlayed;
	private PlayZone playzone;
	private Hand hand;
	private GameTopMenu topMenu;
	private PlayerBalk playerbalk;

	private CardSet cardSet;

	private ExternalCardViewer cardViewer;

	private Player player;

	public GUIGame(Turn turn, boolean newTurn, String player){
		cardsPlayed = new ArrayList<miniCard>();
		this.player = turn.getGame().getPlayerByName(player);
		this.turn = turn;
		createGameGUI();
		cardViewer = new ExternalCardViewer();
	}

	public Player getPlayer(){
		return player;
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

	public ExternalCardViewer getCardViewer(){
		return cardViewer;
	}

	public void loadCardsPlayed(){
		cardsPlayed.clear();
		List<JSONObject> JSONCardsPlayed = turn.getGame().getBoard().getPlayedCards();
		for(int i = 0; i< JSONCardsPlayed.size(); i++){
			JSONObject card = JSONCardsPlayed.get(i);
			miniCard m = new miniCard(card,this);
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
		center.setPadding(new Insets(10,0,10,0));
		VBox gameZone = createGameZone();
		center.getChildren().add(gameZone);
		center.setAlignment(Pos.TOP_CENTER);
		root.setCenter(center);

		hand = new Hand(this);

		root.setBottom(hand.getHand());
		setHand();
	}

	private void setHand(){
		playerbalk.getActionButton().setHand(hand);
		playerbalk.getPlayButton().setHand(hand);
	}

	private VBox createGameZone(){
		VBox container = new VBox();
		container.setSpacing(20);
		cardSet = new CardSet(this);
		playzone = new PlayZone(cardsPlayed);
		playerbalk = new PlayerBalk(this);


		container.getChildren().addAll(cardSet.getCardset(), playzone.getPlayZone(),playerbalk.getPlayerBalk());
		return container;

	}




}
