package net.ultradev.dominion.specialScreens;



import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.ultradev.domininion.GUIUtils.Carousel;
import net.ultradev.dominion.DominionGUIMain;
import net.ultradev.dominion.cardsGUI.GUICard;
import net.ultradev.dominion.game.Turn;
import net.ultradev.dominion.game.card.Card;
import net.ultradev.dominion.game.card.action.actions.SpyAction;
import net.ultradev.dominion.gameGUI.GUIGame;

public class RevealedCards{
	private BorderPane root;
	public RevealedCards(JSONObject response, Turn turn){
		createScreen(response,turn);
	}

	public BorderPane getRoot(){
		return root;
	}

	private void createScreen(JSONObject response, Turn turn) {
		root = new BorderPane();
		root.setId("Menu");
		HBox center = new HBox();
		center.setAlignment(Pos.CENTER);
		center.setPadding(new Insets(10,0,10,0));
		center.setSpacing(50);
		Text playerName = new Text("Revealed cards from: " + response.getString("player"));
		playerName.setId("label");

		Button button = new Button();
		button.setPrefHeight(36);
		button.setPadding(new Insets(0,10,0,10));
		button.setId("gameButton");
		button.setText("GO TO NEXT PLAYER");
		button.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent e){
						standardActions(turn);

			}
		});
		center.getChildren().addAll(playerName,button);
		if(turn.getActiveAction() instanceof SpyAction){
			button.setText("DISCARD CARD");

			Button selectButton = new Button();
			selectButton.setPrefHeight(36);
			selectButton.setPadding(new Insets(0,10,0,10));
			selectButton.setId("gameButton");
			selectButton.setText("KEEP CARD");
			selectButton.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent e){
					String cardTitle = response.getJSONArray("reveal").getJSONObject(0).getString("name");
					Card card = turn.getGame().getGameServer().getCardManager().get(cardTitle);
					JSONObject newResponse = turn.getActiveAction().selectCard(turn , card);
					if(newResponse.getString("result").equals("REVEAL")){
						showNextPlayer(newResponse, turn);
					}
					if(newResponse.getString("result").equals("DONE")){
						GUIGame game = new GUIGame(turn,false);
			         	DominionGUIMain.setRoot(game.getRoot());
					}

			}
			});
			center.getChildren().add(selectButton);
		}



		root.setCenter(center);

		HBox cards =  new HBox();
		cards.setAlignment(Pos.CENTER);
		cards.setPadding(new Insets(10,0,10,0));
		cards.setSpacing(10);

		JSONArray listCards = response.getJSONArray("reveal");
		ArrayList<GUICard> listGUICards = new ArrayList<GUICard>();

		for(int i = 0; i<listCards.size(); i++){
			GUICard card = new GUICard(listCards.getJSONObject(i));
			listGUICards.add(card);
		}
		Carousel c = new Carousel();
		c.setCarousel(cards, listGUICards);
		root.setBottom(cards);
	}

	private void standardActions(Turn turn){
		JSONObject newResponse = turn.stopAction();
		if(newResponse.getString("result").equals("REVEAL")){
			showNextPlayer(newResponse, turn);
		}
		if(newResponse.getString("result").equals("DONE")){
			GUIGame game = new GUIGame(turn,false);
         	DominionGUIMain.setRoot(game.getRoot());
		}
		if(newResponse.getString("result").equals("SELECT_CARD_HAND")){
			PlayerConfirm playerConfirm = new PlayerConfirm(turn.getGame(),newResponse);
	    	DominionGUIMain.setRoot(playerConfirm.getRoot());
		}
	}

	private void showNextPlayer(JSONObject response, Turn turn){
		createScreen(response,turn);
		DominionGUIMain.setRoot(root);
	}
}
