package net.ultradev.dominion.cardsGUI;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import net.sf.json.JSONObject;
import net.ultradev.dominion.gameGUI.GUIGame;

public class MiniCard {

	private JSONObject Jcard;
	private GUIGame parent;

	private VBox card;
	private String type;
	private String title;
	private Image img;



	public MiniCard(JSONObject card, GUIGame parent){
		this.parent = parent;
		Jcard = card;
		this.title = card.getString("name");
		this.type = card.getString("type").toLowerCase();
		this.img = new Image("File:Images/cards/" + title.toLowerCase() +".jpg");
		createMiniCard();

	}


	public VBox getCard(){
		return card;
	}


	private void createMiniCard(){
		int width = 120;
		int height = 60;
		card = createVBox(type,width);
		VBox titleBox = createTitle(40);
		ImageView iv = createImg(img,width,height);
		card.getChildren().addAll(titleBox,iv);
		card.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent mouseEvent){
				VBox newCard = new GUICard(Jcard).getCard();
				parent.getCardViewer().setRoot(newCard);
			}
		});
	}


	private VBox createVBox(String type, int width){
		VBox vbox = new VBox();
		vbox.setMinHeight(100);
		vbox.setMaxHeight(120);

		if(type.contains("action -")){
			vbox.setId("action");
		}else{
			vbox.setId(type);
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

	private ImageView createImg(Image img,int width, int height){
		ImageView iv = new ImageView();
		iv.setImage(img);
		iv.setFitHeight(height);
		iv.setFitWidth(width);
		return iv;
	}







}
