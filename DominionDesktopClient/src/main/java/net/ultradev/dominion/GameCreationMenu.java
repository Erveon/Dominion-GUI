package net.ultradev.dominion;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import net.ultradev.dominion.game.local.LocalGame;
import net.ultradev.dominion.gameGUI.GUtils;

public class GameCreationMenu {

	private BorderPane root;
	private TextField[] listInput;
	private GUtils utils = new GUtils();

	public GameCreationMenu(){
		listInput = new TextField[4];
		createGameCreationMenu();
	}

	public BorderPane getRoot(){
		return root;
	}

	private void createGameCreationMenu(){

		root = new BorderPane();
		root.setId("Menu");

		VBox container = labelBox();
		container.setAlignment(Pos.CENTER);



		root.setCenter(container);
		HBox buttonContainer = new HBox();
		buttonContainer.setAlignment(Pos.CENTER);
		buttonContainer.setPadding(new Insets(0,0,20,0));

		Button createGameBtn = utils.createButton("Create Game", 300, 100);
		createGameBtn.setAlignment(Pos.CENTER);

		createGameBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            	GameServer gs = new GameServer();
            	gs.getUtils().setDebugging(false);
                LocalGame localGame = gs.getGameManager().createGame(null);

                for(int i=0;i<4;i++){
                	if(!listInput[i].getText().equals(""))
                	{
                		localGame.addPlayer(listInput[i].getText());
                	}
                }

                localGame.start();

                PlayerConfirm pc = new PlayerConfirm(localGame,true);

            	DominionGUIMain.setRoot(pc.getRoot());

            }
        });
		buttonContainer.getChildren().add(createGameBtn);
		root.setBottom(buttonContainer);
	}

	private VBox labelBox(){
		VBox labelBox = new VBox();
		labelBox.setSpacing(20);
		for(int i=0;i<4;i++){
			HBox hbox = new HBox();
			hbox.setAlignment(Pos.CENTER);
			hbox.setSpacing(50);
			Text playerLabel = createPlayerLabel(i);
			TextField textfield = createTextField();
			listInput[i]=textfield;
			hbox.getChildren().addAll(playerLabel,textfield );
			labelBox.getChildren().add(hbox);
		}

		return labelBox;
	}

	private Text createPlayerLabel(int index){
		Text playerLabel = new Text();
		String pLabel = "Player "+(index+1);
		playerLabel.setText(pLabel);
		playerLabel.setFill(Paint.valueOf("white"));

		return playerLabel;
	}

	private TextField createTextField(){
		TextField textField = new TextField();

		return textField;
	}



}
