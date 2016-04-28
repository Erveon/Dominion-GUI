package net.ultradev.dominion.gameGUI;


import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import net.ultradev.dominion.DominionGUIMain;
import net.ultradev.dominion.PlayerConfirm;
import net.ultradev.dominion.game.Turn;
import net.ultradev.dominion.game.player.Player;

public class PlayerBalk {
	private HBox playerBalk;
	private List<Player> players;
	private Player currentPlayer;
	private Turn turn;
	private Circle[] circlePhases;
	private GUIGame parent;


	public PlayerBalk(Turn currentTurn, GUIGame parent){
		this.players = currentTurn.getGame().getPlayers();
		this.currentPlayer = currentTurn.getPlayer();
		turn = currentTurn;
		this.parent = parent;
		createPlayerBalk();
	}

	public HBox getPlayerBalk(){
		return playerBalk;
	}

	private void createPlayerBalk(){
		playerBalk = new HBox();
		int spacing = 10;
		int width = 570;
		HBox left = new HBox();
		left.setMaxWidth(width);
		left.setMinWidth(width);
		left.setSpacing(spacing);

		HBox playerlist = createPlayerList();
		HBox phase = createPhaseList();

		left.getChildren().addAll(playerlist,phase);

		HBox right = new HBox();
		right.setSpacing(spacing);
		right.setAlignment(Pos.CENTER_RIGHT);
		right.setMaxWidth(width);
		right.setMinWidth(width);


		Button nextPhase = createButton("PLAY TREASURE");
		nextPhase.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	circlePhases[0].setFill(Color.rgb(236, 240, 241));
            	circlePhases[1].setFill(Color.rgb(241, 196, 15));

            }
        });
		Button endTurn = createButton("END TURN");
		endTurn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	circlePhases[1].setFill(Color.rgb(236, 240, 241));
            	circlePhases[2].setFill(Color.rgb(241, 196, 15));
            	turn.end();


            	PlayerConfirm playerConfirm = new PlayerConfirm(turn.getGame(),false);
            	DominionGUIMain.setRoot(playerConfirm.getRoot());

            }
        });

		right.getChildren().addAll(nextPhase,endTurn);
		playerBalk.getChildren().addAll(left,right);
	}



	private HBox createPlayerList(){
		HBox hbox = new HBox();
		hbox.setPrefHeight(36);
		hbox.setId("playerbalk");

		HBox playerTitle = createPlayerBox("PLAYERS");
		playerTitle.setId("playerTitle");

		hbox.getChildren().add(playerTitle);

		for(int i = 0;i< players.size(); i++ ){
			HBox player = createPlayerBox(players.get(i).getDisplayname());
			if(players.get(i).getDisplayname() == currentPlayer.getDisplayname()){
			player.setId("currentState");
			}
			hbox.getChildren().add(player);
		}

		return hbox;
	}

	private HBox createPhaseList(){
		HBox hbox = new HBox();
		hbox.setSpacing(5);
		hbox.setAlignment(Pos.CENTER);
		hbox.setPadding(new Insets(0,10,0,0));
		HBox phase = createPlayerBox("PHASE");
		hbox.setId("playerbalk");
		phase.setId("playerTitle");
		hbox.getChildren().add(phase);
		circlePhases = new Circle[3];
		for(int i=0;i<circlePhases.length;i++){
			circlePhases[i] = createCircle();
			hbox.getChildren().add(circlePhases[i]);
		}
		circlePhases[0].setFill(Color.rgb(241, 196, 15));


		return hbox;
	}

	private HBox createPlayerBox(String tekst){
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(0,10,0,10));
		hbox.setAlignment(Pos.CENTER);
		Text playerName = new Text();
		playerName.setText(tekst);
		hbox.getChildren().add(playerName);
		return hbox;
	}

	private Circle createCircle(){
		Circle circle = new Circle();
		circle.setRadius(7.5);
		circle.setFill(Color.rgb(236, 240, 241));
		return circle;
	}

	private Button createButton(String text){
		Button btn = new Button();
		btn.setText(text);
		btn.setPrefHeight(36);
		btn.setId("gameButton");
		btn.setPadding(new Insets(0,10,0,10));
		return btn;
	}
}
