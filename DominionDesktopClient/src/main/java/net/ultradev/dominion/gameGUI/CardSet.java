package net.ultradev.dominion.gameGUI;

import java.util.ArrayList;

import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.sf.json.*;
import net.ultradev.dominion.game.*;

public class CardSet {
	private VBox cardset;
	private Board board;
	private Turn turn;
	private GUIGame guiGame;
	private VBox rowContainer;



	private ArrayList<KingdomCard>  kingdomCards;

	private KingdomCard selectedKingdomCard;

	public CardSet(GUIGame parent ){
		this.turn = parent.getTurn();
		this.board = turn.getGame().getBoard();
		this.guiGame = parent;
		kingdomCards = new ArrayList<KingdomCard>();
		cardset = createCardSets();

	}

	public VBox getCardset(){
		return cardset;
	}

	public GUIGame getGUIGame(){
		return guiGame;
	}
	public Board getBoard(){
		return board;
	}
	public KingdomCard getSelectedKingdomCard(){
		return selectedKingdomCard;
	}

	public void setBorder(){
		try{
		if(selectedKingdomCard.getCost() > turn.getBuypower()){
			selectedKingdomCard.getCard().setStyle("-fx-border-color: red; -fx-border-width: 4");
		}else{
			selectedKingdomCard.getCard().setStyle("-fx-border-color: white; -fx-border-width: 4");
		}
		for(int i = 0; i< kingdomCards.size();i++){
			if(!kingdomCards.get(i).equals(selectedKingdomCard)){
				kingdomCards.get(i).getCard().setStyle("-fx-border: none");
			}
		}}
		catch(Exception e){

		}
	}

	public void selectKingdomCard(KingdomCard selectedCard){
		selectedKingdomCard =selectedCard;
		setBorder();

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
			VBox card = kingdomCards.get(startindex + i).getCard();
			row.getChildren().add(card);
		}
		vbox.getChildren().add(row);

		return vbox;
	}

	private void createkingdomCards(){

		JSONArray action = board.getAsJson().getJSONArray("action");
		for(int i = 0; i<10; i++){
			JSONObject actionCard = action.getJSONObject(i);
			kingdomCards.add(new KingdomCard(actionCard,"action",this));
		}
	}

	private VBox createThirdRow(){

		JSONArray treasure =  board.getAsJson().getJSONArray("treasure");
		JSONObject curse = board.getAsJson().getJSONArray("curse").getJSONObject(0);
		KingdomCard kcard1 = new KingdomCard(curse, "curse", this);
		kingdomCards.add(kcard1);
		VBox card1 = kcard1.getCard();

		VBox card5 = createDiscardCard();

		VBox row = generateLastRows(card1,treasure,"treasure",card5);
		return row;
	}
	private VBox createFourthRow(){
		JSONArray victory =  board.getAsJson().getJSONArray("victory");

		VBox trash = createDeckType("trash",board.getAsJson().getJSONArray("trash").size());
		VBox deck = createDeckType("Deck",turn.getPlayer().getDeck().size());
		VBox row = generateLastRows(trash,victory,"victory",deck);
		return row;
	}

	private VBox generateLastRows(VBox card1, JSONArray middleCards, String type, VBox card5 ){
		VBox vbox = new VBox();
		vbox.setFillWidth(true);
		TilePane row = new TilePane();
		row.setPrefColumns(5);
		row.setHgap(10);
		row.getChildren().add(card1);
		for(int i = 0; i<3;i++){
			KingdomCard kcard = new KingdomCard(middleCards.getJSONObject(i),type,this);
			VBox cardX = kcard.getCard();
			kingdomCards.add(kcard);
			row.getChildren().add(cardX);
		}
		row.getChildren().add(card5);
		vbox.getChildren().add(row);
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

	private VBox createDiscardCard(){
		VBox vbox = new VBox();
		vbox.setId("discard");
		vbox.setAlignment(Pos.CENTER);
		Text discard = new Text();
		discard.setText("DISCARD");
		vbox.getChildren().add(discard);
		return vbox;
	}





}
