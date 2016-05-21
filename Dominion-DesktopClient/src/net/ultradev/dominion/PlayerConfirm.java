package net.ultradev.dominion;


import javafx.event.ActionEvent;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import net.sf.json.JSONObject;
import net.ultradev.dominion.game.Game;
import net.ultradev.dominion.game.card.action.Action;
import net.ultradev.dominion.gameGUI.CustomButton;
import net.ultradev.dominion.gameGUI.GUIGame;
import net.ultradev.dominion.gameGUI.GUtils;

public class PlayerConfirm {
		private BorderPane root;
		private Game localGame;
		private GUtils utils = new GUtils();
		private Button readyBtn;


		public PlayerConfirm(Game game, boolean sameTurn){
			this.localGame = game;
			if(!sameTurn){
				game.endTurn();
			}
			String playername = localGame.getTurn().getPlayer().getDisplayname();
			createScreen(playername);

			readyBtn.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	GUIGame game = new GUIGame(localGame.getTurn(),false,playername);
	             	DominionGUIMain.setRoot(game.getRoot());
	            }
	        });
		}

		public PlayerConfirm(Game game, JSONObject response){
			this.localGame = game;
			createScreen(response.getString("player"));
			readyBtn.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	CardSelection cardSelection = new CardSelection(game.getTurn(),response);
	             	DominionGUIMain.setRoot(cardSelection.getRoot());
	            }
			});
		}

		public BorderPane getRoot(){
			return root;
		}

		private void createScreen(String playername){
			double width = 300;
			double height = 100;
			root = new BorderPane();
			root.setId("Menu");
			GUtils functions = new GUtils();



			VBox center = new VBox();
			center.setAlignment(Pos.CENTER);


			HBox nameContainer = utils.createCenterHBox("");
			nameContainer.setPrefHeight(100);
			Text playerName = new Text();

			playerName.setText(playername);
			playerName.setId("PlayerReady");

			nameContainer.getChildren().add(playerName);

			readyBtn = utils.CreateButton("READY", width, height);


			center.getChildren().addAll(nameContainer,readyBtn);
			root.setCenter(center);

			HBox bottom = new HBox();
			bottom.setAlignment(Pos.CENTER_RIGHT);
			Button exitBtn = functions.createExit();
			bottom.getChildren().add(exitBtn);
			root.setBottom(bottom);
		}



	}

