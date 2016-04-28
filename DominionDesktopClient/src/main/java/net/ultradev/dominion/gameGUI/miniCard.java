package net.ultradev.dominion.gameGUI;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import net.ultradev.dominion.game.card.Card;

public class miniCard {
	private VBox card;
	private String type;
	private String title;

	private Image img;



	public miniCard(String title, String type){
		this.title = title;
		this.type = type;
		this.img = new Image("File:Images/copper.jpg");
		createMiniCard();

	}


	public VBox getCard(){
		return card;
	}


	private void createMiniCard(){
		int width = 110;
		int height = 80;
		card = createVBox(type,width);
		VBox titleBox = createTitle(50);
		ImageView iv = createImg(img,width,height);
		card.getChildren().addAll(titleBox,iv);


	}


	private VBox createVBox(String type, int width){
		VBox vbox = new VBox();
		vbox.setId(type);
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







}
