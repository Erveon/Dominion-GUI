package net.ultradev.dominion;


import javafx.event.ActionEvent;
import javax.servlet.http.HttpSession;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import net.ultradev.dominion.game.Game;
import net.ultradev.dominion.game.Turn;
import net.ultradev.dominion.game.local.LocalGame;
import net.ultradev.dominion.gameGUI.CustomButton;
import net.ultradev.dominion.gameGUI.GUIGame;
import net.ultradev.dominion.gameGUI.GUtils;

public class PlayerConfirm {
		private BorderPane root;
		private Game localGame;
		private GUtils utils = new GUtils();


		public PlayerConfirm(Game game, boolean firstTurn){
			this.localGame = game;
			if(!firstTurn){
				game.endTurn();
			}
			createScreen();
		}

		public BorderPane getRoot(){
			return root;
		}

		private void createScreen(){
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
			playerName.setText(localGame.getTurn().getPlayer().getDisplayname());
			playerName.setId("PlayerReady");
			nameContainer.getChildren().add(playerName);

			Button readyButton = new CustomButton("READY", width, height).getButton();
			readyButton.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	GUIGame game = new GUIGame(localGame.getTurn());
	             	DominionGUIMain.setRoot(game.getRoot());
	            }
	        });

			center.getChildren().addAll(nameContainer,readyButton);
			root.setCenter(center);

			HBox bottom = new HBox();
			bottom.setAlignment(Pos.CENTER_RIGHT);
			Button exitBtn = functions.createExit();
			bottom.getChildren().add(exitBtn);
			root.setBottom(bottom);
		}



	}

