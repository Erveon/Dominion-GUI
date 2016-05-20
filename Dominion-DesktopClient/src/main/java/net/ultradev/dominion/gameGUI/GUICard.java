package net.ultradev.dominion.gameGUI;



import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.image.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import net.sf.json.JSONObject;
import net.ultradev.dominion.game.card.Card;

public class GUICard {
	private VBox cardBox;
	private String cardDesription;
	private String type;
	private String title;
	private int cost;
	private Image img;
	private Hand parent;

	private boolean showCard;

	private Card sourceCard;

	public GUICard(Card card, Hand parent){
		sourceCard = card;
		this.parent = parent;
		this.title = card.getName();
		type = card.getType().toString().toLowerCase();

		this.cardDesription = card.getDescription();
		this.cost = card.getCost();
		this.img = new Image("File:Images/copper.jpg");
		showCard = false;
		createCard();

	}

	public GUICard(){
		cardBox = new VBox();
		ImageView iv = createImg(new Image("File:Images/Card_back.jpg"),220,310);
		cardBox.getChildren().add(iv);

	}

	public GUICard(JSONObject card){
		title = card.getString("name");
		type = card.getString("type").toLowerCase();
		cardDesription = card.getString("description");
		cost = card.getInt("cost");
		this.img = new Image("File:Images/copper.jpg");
		showCard = true;
		createCard();

		}

	public VBox getCard(){
		return cardBox;
	}

	public String getType(){
		return type;
	}

	public int getCost(){
		return cost;
	}

	public String getTitle(){
		return title;
	}

	public Card getSourceCard(){
		return sourceCard;
	}



	private void createCard(){
		int width = 220;
		int height = 160;
		cardBox = createVBox(width);
		cardBox.getStyleClass().add("Card");
		VBox titleBox = createTitle(30);
		ImageView iv = createImg(img,width,height);
		VBox description = createDescription();
		HBox bottom = createBottomCard();
		cardBox.getChildren().addAll(titleBox,iv,description,bottom);

		if(!showCard){
			this.getCard().setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
						if(checkActive() && !parent.getIfSelectionScreen()){
							playCard();
						}
						else{
							selectCard();

						}

					}
		    }
		});}
	}

	private VBox createVBox(int width){
		VBox vbox = new VBox();
		vbox.setId(type);
		if(type.contains("-")){
			vbox.setId("action");
		}

		vbox.setFillWidth(true);
		vbox.setPrefWidth(width);

		return vbox;
	}

	private VBox createTitle(int height){
		VBox titleBox = new VBox();
		titleBox.setPrefHeight(height);
		titleBox.setAlignment(Pos.CENTER);
		Text cardTitle = new Text();
		cardTitle.setText(title.toUpperCase());
		cardTitle.setId("cardtitle");
		titleBox.getChildren().add(cardTitle);
		return titleBox;
	}

	private ImageView createImg(Image img, int width, int height){
		ImageView iv = new ImageView();
		iv.setImage(img);
		iv.setFitHeight(height);
		iv.setFitWidth(width);
		return iv;
	}

	private VBox createDescription(){
		VBox vbox = new VBox();

		vbox.setPrefHeight(100);
		vbox.setMaxHeight(100);
		vbox.setAlignment(Pos.CENTER);
		vbox.setId("description");
		Text cardTitle = new Text();
		cardTitle.setText(cardDesription);
		cardTitle.setId("cardtitle");
		if(cardDesription.length()>100){
			cardTitle.setStyle("-fx-font-size: 12");
		}

		cardTitle.setWrappingWidth(200);
		cardTitle.setTextAlignment(TextAlignment.CENTER);


		vbox.getChildren().add(cardTitle);

		return vbox;
	}

	private HBox createBottomCard(){
		HBox hbox = new HBox();
		hbox.setId("BottomCard");
		hbox.setMinHeight(18);
		hbox.setFillHeight(true);
		hbox.setPadding(new Insets(5,0,5,0));

		VBox boxValue = new VBox();
		boxValue.setPrefWidth(110);
		boxValue.setAlignment(Pos.CENTER_LEFT);
		boxValue.setPadding(new Insets(0,0,0,10));
		Text bottomValue = new Text();
		bottomValue.setText(Integer.toString(cost));
		bottomValue.setId("valueCard");
		boxValue.getChildren().add(bottomValue);

		VBox boxType = new VBox();
		boxType.setPrefWidth(110);
		boxType.setAlignment(Pos.CENTER_RIGHT);
		boxType.setPadding(new Insets(0,10,0,0));
		Text bottomType = new Text();

		bottomType.setText(type.toUpperCase());
		bottomType.setId("cardtitle");
		boxType.getChildren().add(bottomType);

		hbox.getChildren().addAll(boxValue,boxType);
		return hbox;
	}

	private boolean checkActive(){
		if(parent.getSelectedCard() != null){
			return parent.getSelectedCard().equals(this);
		}
		return false;

	}

	private void selectCard(){
		parent.setActiveGCard(this);
		if(parent.getParent() != null){
			CardSet cardset = parent.getParent().getCardSet();
			if(cardset.getSelectedKingdomCard() != null)
			{
				parent.getParent().getCardSet().removeBorder();
			}
		}
	}

	public void playCard(){

		//Kaart spelen en response verzenden naar button

		JSONObject response = parent.getParent().getTurn().playCard(title);
		//System.out.println(response);
		if(response.getString("response").equals("OK")){
			removeCardGUI();
			if(!response.getString("result").equals("DONE")){
				parent.getParent().getPlayerbalk().getActionButton().setAction(response);
				parent.getParent().getPlayerbalk().getStopActionButton().setActive(true);
			}

		}

	}

	public void removeCardGUI(){
		parent.getCards().remove(this);
		parent.reloadCards(false);
		parent.setActiveGCard(null);

		if(!parent.getIfSelectionScreen()){
			parent.getParent().getTopMenu().reloadCounters();
			parent.getParent().loadCardsPlayed();
		}

	}






}

