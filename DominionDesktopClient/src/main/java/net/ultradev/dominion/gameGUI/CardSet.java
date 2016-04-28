package net.ultradev.dominion.gameGUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.ultradev.dominion.game.Board;
import net.ultradev.dominion.game.Turn;
import net.ultradev.dominion.game.local.LocalGame;

public class CardSet {
	private VBox cardset;
	private Board board;
	private Turn turn;

	//TIJDELIjk
	private String[] lijstNamen = {"TEST","TEST","TEST","TEST","TEST","TEST","TEST","TEST","TEST","TEST"};

	public CardSet(Turn turn){
		this.turn = turn;
		this.board = turn.getGame().getBoard();

		cardset = createCardSets();
	}

	public VBox getCardset(){
		return cardset;
	}

	private VBox createCardSets(){
		VBox container = new VBox();
		container.setSpacing(10);
		container.setPrefWidth(1142);
		container.setFillWidth(true);




		VBox row = createKingdomRows(lijstNamen,0);
		VBox row2 = createKingdomRows(lijstNamen,5);
		VBox row3 = createThirdRow();
		VBox row4 = createFourthRow();
		container.getChildren().addAll(row,row2,row3,row4);

		return container;

	}

	private VBox createKingdomRows(String[] lijst, int startindex){

		VBox vbox = new VBox();
		vbox.setFillWidth(true);
		TilePane row = new TilePane();
		row.setPrefColumns(5);
		row.setHgap(10);
		for(int i=0; i<5; i++){
			VBox card = kingdomCard(lijst[startindex+i],"1","10");
			row.getChildren().add(card);
		}

		vbox.getChildren().add(row);

		return vbox;
	}

	private VBox kingdomCard(String title, String value, String count){
		VBox vbox = createCard(title,"action",value,count);

		return vbox;
	}

	private VBox createThirdRow(){
		VBox vbox = new VBox();
		vbox.setFillWidth(true);
		TilePane row = new TilePane();
		row.setPrefColumns(5);
		row.setHgap(10);
		JSONArray treasure =  board.getAsJson().getJSONArray("treasure");



		VBox card1 = createCard("Curse","curse","0","0");
		VBox card2 = createCard("copper","treasure",getSupply(treasure,"copper").getString("cost"),getSupply(treasure,"copper").getString("amount"));
		VBox card3 = createCard("silver","treasure",getSupply(treasure,"copper").getString("cost"),getSupply(treasure,"silver").getString("amount"));
		VBox card4 = createCard("gold","treasure",getSupply(treasure,"copper").getString("cost"),getSupply(treasure,"gold").getString("amount"));
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

		VBox card1 = createDeck("trash",0);
		VBox card2 = createCard("estate","victory",getSupply(victory,"estate").getString("cost"),getSupply(victory,"estate").getString("amount"));
		VBox card3 = createCard("duchy","victory",getSupply(victory,"duchy").getString("cost"),getSupply(victory,"duchy").getString("amount"));
		VBox card4 = createCard("province","victory",getSupply(victory,"province").getString("cost"),getSupply(victory,"province").getString("amount"));
		VBox card5 = createDeck("Deck",turn.getPlayer().getDeck().size());
		row.getChildren().addAll(card1,card2,card3,card4,card5);
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
		valueCard.setText(String.valueOf(value));
		HBox spaceBetween = new HBox();
		spaceBetween.setPrefWidth(150);
		Text countCard = new Text();
		countCard.setId("countCard");
		countCard.setText((count));
		cardinfo.getChildren().addAll(valueCard,spaceBetween,countCard);
		vbox.getChildren().addAll(cardtitle,cardinfo);
		return vbox;
	}

	private VBox createDeck(String title,int count){
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

	private JSONObject getSupply(JSONArray lijst,String naam){
		JSONObject obj = new JSONObject();
		for(int i =0; i<lijst.size();i++){
			String name = lijst.getJSONObject(i).getString("name");
			if(name.equals(name)){
				obj = lijst.getJSONObject(i);		}

		}
		return obj;
	}


}
