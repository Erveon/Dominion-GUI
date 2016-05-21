package net.ultradev.dominion;

import javafx.event.ActionEvent;


import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import net.ultradev.dominion.gameGUI.CustomButton;
import net.ultradev.dominion.gameGUI.GUtils;

public class LocalGameMenu {
	private BorderPane root;
	private GUtils utils = new GUtils();

	public LocalGameMenu(){
		createLocalGameMenu();
	}

	public BorderPane getRoot(){
		return root;
	}

	private void createLocalGameMenu(){
		double width = 300;
		double height = 100;
		root = new BorderPane();
		root.setId("Menu");
		GUtils functions = new GUtils();

		VBox center = new VBox();
		center.setSpacing(10);
		center.setAlignment(Pos.CENTER);

		Button newGame = utils.CreateButton("Create new Game", width, height);
		newGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                GameCreationMenu gcm = new GameCreationMenu();

            	DominionGUIMain.setRoot(gcm.getRoot());

            }
        });
		Button loadGame = utils.CreateButton("Load Game", width, height);
		center.getChildren().addAll(newGame,loadGame);
		root.setCenter(center);

		HBox bottom = new HBox();
		bottom.setAlignment(Pos.CENTER_RIGHT);
		Button exitBtn = functions.createExit();
		bottom.getChildren().add(exitBtn);
		root.setBottom(bottom);
	}



}
