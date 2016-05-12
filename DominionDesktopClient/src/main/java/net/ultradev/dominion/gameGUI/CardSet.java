package net.ultradev.dominion.gameGUI;

import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import net.sf.json.*;
import net.ultradev.dominion.game.*;

public class CardSet {
	private VBox cardset;
	private Board board;
	private Turn turn;
	private GUIGame parent;
	private VBox rowContainer;

	//TIJDELIjk
	private VBox[] kingdomCards;

	public CardSet(Turn turn,GUIGame parent ){
		this.turn = turn;
		this.board = turn.getGame().getBoard();
		this.parent = parent;

		cardset = createCardSets();
	}

	public VBox getCardset(){
		return cardset;
	}

	public void loadRows(){
		rowContainer.getChildren().clear();
		VBox[] rows = new VBox[4];
		rows[0] = createKingdomRows(0);
		rows[1] = createKingdomRows(5);
		rows[2] = createThirdRow();
		rows[3] = createFourthRow();
		rowContainer.getChildren().addAll(rows);
	}

	private VBox createCardSets(){
		rowContainer = new VBox();
		rowContainer.setSpacing(10);
		rowContainer.setPrefWidth(1142);
		rowContainer.setFillWidth(true);

		createkingdomCards();
		loadRows();


		return rowContainer;

	}

	private VBox createKingdomRows(int startindex){

		VBox vbox = new VBox();
		vbox.setFillWidth(true);
		TilePane row = new TilePane();
		row.setPrefColumns(5);
		row.setHgap(10);
		for(int i=0; i<5; i++){
			VBox card = kingdomCards[startindex + i];
			row.getChildren().add(card);
		}
		vbox.getChildren().add(row);

		return vbox;
	}

	private void createkingdomCards(){
		kingdomCards = new VBox[10];
		JSONArray action = board.getAsJson().getJSONArray("action");
		for(int i = 0; i<10; i++){
			JSONObject actiecard = action.getJSONObject(i);
			VBox vbox = createCard(actiecard.getString("name"),"action",actiecard.getString("cost"),actiecard.getString("amount"));
			kingdomCards[i] = vbox;
		}
	}

	private VBox createThirdRow(){
		VBox vbox = new VBox();
		vbox.setFillWidth(true);
		TilePane row = new TilePane();
		row.setPrefColumns(5);
		row.setHgap(10);

		JSONArray treasure =  board.getAsJson().getJSONArray("treasure");
		JSONObject curse = board.getAsJson().getJSONArray("curse").getJSONObject(0);

		VBox card1 = createCard("Curse","curse",curse.getString("cost"),curse.getString("amount"));
		VBox card2 = createCard("copper","treasure",treasure.getJSONObject(0).getString("cost"),treasure.getJSONObject(0).getString("amount"));
		VBox card3 = createCard("silver","treasure",treasure.getJSONObject(1).getString("cost"),treasure.getJSONObject(1).getString("amount"));
		VBox card4 = createCard("gold","treasure",treasure.getJSONObject(2).getString("cost"),treasure.getJSONObject(2).getString("amount"));
		VBox card5 = new VBox();
		card5.setId("discard");
		card5.setAlignment(Pos.CENTER);
		Text discard = new Text();
		discard.setText("DISCARD");
		card5.getChildren().add(discard);
		row.getChildren().addAll(card1,card2,card3,card4,card5);
		vbox.getChildren().add(row);
		return vbox;
	}
	private VBox createFourthRow(){
		VBox vbox = new VBox();
		vbox.setFillWidth(true);
		TilePane row = new TilePane();
		row.setPrefColumns(5);
		row.setHgap(10);

		JSONArray victory =  board.getAsJson().getJSONArray("victory");

		VBox trash = createDeckType("trash",board.getAsJson().getJSONArray("trash").size());
		VBox card2 = createCard("estate","victory",victory.getJSONObject(0).getString("cost"),victory.getJSONObject(0).getString("amount"));
		VBox card3 = createCard("duchy","victory",victory.getJSONObject(1).getString("cost"),victory.getJSONObject(1).getString("amount"));
		VBox card4 = createCard("province","victory",victory.getJSONObject(2).getString("cost"),victory.getJSONObject(2).getString("amount"));
		VBox deck = createDeckType("Deck",turn.getPlayer().getDeck().size());
		row.getChildren().addAll(trash,card2,card3,card4,deck);
		vbox.getChildren().add(row);
		return vbox;
	}


	private VBox createCard(String title, String id,String value, String count){
		VBox vbox = new VBox();
		vbox.setPrefSize(220, 70);
		vbox.setId(id);
		vbox.setFillWidth(true);
		vbox.setAlignment(Pos.TOP_CENTER);
		vbox.setPadding(new Insets(5,0,0,0));

		Text cardtitle = new Text();
		cardtitle.setId("cardtitle");
		cardtitle.setText(title.toUpperCase());

		HBox cardinfo = new HBox();
		cardinfo.setAlignment(Pos.CENTER);

		Text valueCard = new Text();
		valueCard.setId("valueCard");
		valueCard.setText(value);

		HBox spaceBetween = new HBox();
		spaceBetween.setPrefWidth(150);
		Text countCard = new Text();
		countCard.setId("countCard");
		countCard.setText((count));
		cardinfo.getChildren().addAll(valueCard,spaceBetween,countCard);
		vbox.getChildren().addAll(cardtitle,cardinfo);

		vbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
			  @Override
			    public void handle(MouseEvent mouseEvent) {
			        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
			        	buyCard(title);
			        	loadRows();
			        }
			    }
			});

		return vbox;
	}

	private VBox createDeckType(String title,int count){
		VBox vbox = new VBox();
		vbox.setPrefSize(220, 70);
		vbox.setId("deck");
		vbox.setFillWidth(true);
		vbox.setAlignment(Pos.TOP_CENTER);
		vbox.setPadding(new Insets(5,0,0,0));
		Text cardtitle = new Text();
		cardtitle.setId("cardtitle");
		cardtitle.setText(title.toUpperCase());
		HBox cardinfo = new HBox();
		cardinfo.setAlignment(Pos.CENTER);
		Text countCard = new Text();
		countCard.setId("deckCount");
		countCard.setText(String.valueOf(count));
		cardinfo.getChildren().add(countCard);
		vbox.getChildren().addAll(cardtitle,cardinfo);
		return vbox;
	}

	private void buyCard(String title){
		try{

		String response = turn.buyCard(title).getString("result");
		if(response.equals("BOUGHT"))		{
			createkingdomCards();
			parent.getTopMenu().reloadCounters();

		}}
		catch(Exception e){
		}

	}

}
