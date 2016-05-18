package net.ultradev.dominion.gameGUI;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import net.sf.json.JSONObject;

public class KingdomCard {
	private JSONObject card;
	private CardSet parent;
	private String title;
	private int cost;
	private int amount;
	private VBox GUICard;

	private Text countCard;

	public KingdomCard(JSONObject card, String id, CardSet parent){
		this.card = card;
		this.title = card.getString("name");
		this.cost = card.getInt("cost");
		this.amount = card.getInt("amount");
		this.parent = parent;
		createCard(id);
	}

	public VBox getCard(){
		return GUICard;
	}

	public int getCost(){
		return cost;
	}

	private void loadAmount(){
		amount = amount -1;
		countCard.setText(Integer.toString(amount));
	}

	private void createCard(String id){
		GUICard = new VBox();
		GUICard.setPrefSize(220, 70);
		GUICard.setId(id);
		GUICard.setFillWidth(true);
		GUICard.setAlignment(Pos.TOP_CENTER);
		GUICard.setPadding(new Insets(5,0,0,0));

		Text cardtitle = new Text();
		cardtitle.setId("cardtitle");
		cardtitle.setText(title.toUpperCase());

		HBox cardinfo = new HBox();
		cardinfo.setAlignment(Pos.CENTER);

		Text valueCard = new Text();
		valueCard.setId("valueCard");
		valueCard.setText(Integer.toString(cost));

		HBox spaceBetween = new HBox();
		spaceBetween.setPrefWidth(150);
		countCard = new Text();
		countCard.setId("countCard");
		countCard.setText(Integer.toString(amount));
		cardinfo.getChildren().addAll(valueCard,spaceBetween,countCard);
		GUICard.getChildren().addAll(cardtitle,cardinfo);

		GUICard.setOnMouseClicked(new EventHandler<MouseEvent>() {
			  @Override
			  public void handle(MouseEvent mouseEvent) {
			        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
			        	if(checkIsActive()){
			        		buyCard();
			        	}else{
			        		selectThis();
			        	}

			        }
			    }
			});
	}

	private boolean checkIsActive(){
		if(parent.getSelectedKingdomCard() != null){
			return parent.getSelectedKingdomCard().equals(this);
		}
		return false;
	}

	private void selectThis(){
		parent.selectKingdomCard(this);
		VBox newCard = new GUICard(card).getCard();
		parent.getGUIGame().getCardViewer().setRoot(newCard);
	}

	private void buyCard(){

		try{
		String response = parent.getGUIGame().getTurn().buyCard(title).getString("result");
		if(response.equals("BOUGHT"))		{
			loadAmount();
			parent.getGUIGame().getTopMenu().reloadCounters();
		}}
		catch(Exception e){

		}
	}
}
