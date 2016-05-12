package net.ultradev.dominion.gameGUI;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.image.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import net.sf.json.JSON;
import net.ultradev.dominion.game.card.Card;

public class GUICard {
	private VBox cardBox;
	private String cardDesription;
	private String type;
	private String title;
	private int cost;
	private Image img;
	private Hand parent;

	private GUtils utils = new GUtils();



	public GUICard(Card card, Hand parent){

		this.parent = parent;
		this.title = card.getName();
		type = utils.setType(card.getName(),card.getDescription());
		this.cardDesription = card.getDescription();
		this.cost = card.getCost();
		this.img = new Image("File:Images/copper.jpg");
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



	private void createCard(){
		int width = 220;
		int height = 160;
		cardBox = createVBox(type,width);
		VBox titleBox = createTitle(title,30);
		ImageView iv = createImg(img,width,height);
		VBox description = createDescription(title);
		HBox bottom = createBottomCard(title);
		cardBox.getChildren().addAll(titleBox,iv,description,bottom);

		this.getCard().setOnMouseClicked(new EventHandler<MouseEvent>() {
		  @Override
		    public void handle(MouseEvent mouseEvent) {
		        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
		        	playCard();
		        }
		    }
		});
	}

	private VBox createVBox(String type, int width){
		VBox vbox = new VBox();
		vbox.setId(type);
		vbox.setFillWidth(true);
		vbox.setPrefWidth(width);

		return vbox;
	}

	private VBox createTitle(String title,int height){
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

	private VBox createDescription(String title){
		VBox vbox = new VBox();

		vbox.setPrefHeight(100);
		vbox.setAlignment(Pos.CENTER);
		vbox.setId("description");
		Text cardTitle = new Text();
		cardTitle.setText(cardDesription);
		cardTitle.setId("cardtitle");
		cardTitle.setWrappingWidth(200);
		cardTitle.setTextAlignment(TextAlignment.CENTER);


		vbox.getChildren().add(cardTitle);

		return vbox;
	}

	private HBox createBottomCard(String title){
		HBox hbox = new HBox();
		hbox.setId("BottomCard");
		hbox.setPrefHeight(18);
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

	public void playCard(){
		//Kaart spelen
		String response = parent.getParent().getTurn().playCard(title).getString("response");
		//System.out.println(parent.getParent().getTurn().getPlayer().getHand());
		if(!response.equals("invalid")){
			parent.getCards().remove(this);
			parent.reloadCards(false);
			parent.getParent().getTopMenu().reloadCounters();
			parent.getParent().loadCardsPlayed();
		}
	}






}

