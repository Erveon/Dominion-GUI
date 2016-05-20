package net.ultradev.dominion.gameGUI;


import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import net.ulradev.dominion.EventHandlers.ActionButtonEventHandler;
import net.ultradev.dominion.game.Turn;
import net.ultradev.dominion.game.player.Player;

public class PlayerBalk {
	private HBox playerBalk;
	private List<Player> players;
	private Player currentPlayer;
	private Turn turn;
	private Circle[] circlePhases;
	private GUIGame parent;

	private CustomButton nextPhase;
	private CustomButton playButton;
	private CustomButton actionButton;
	private CustomButton StopActionButton;




	public PlayerBalk(GUIGame parent){
		turn = parent.getTurn();
		this.players = turn.getGame().getPlayers();
		this.currentPlayer = parent.getPlayer();
		this.parent = parent;
		createPlayerBalk();

	}

	public GUIGame getParent(){
		return parent;
	}

	public HBox getPlayerBalk(){
		return playerBalk;
	}

	public CustomButton getPlayButton(){
		return playButton;
	}
	public CustomButton getActionButton(){
		return actionButton;
	}

	public CustomButton getStopActionButton(){
		return StopActionButton;
	}

	public void changeCircle(int index){
		circlePhases[index].setFill(Color.rgb(236, 240, 241));
		circlePhases[index+1].setFill(Color.rgb(241, 196, 15));
	}

	private void createPlayerBalk(){
		playerBalk = new HBox();
		int spacing = 10;
		int widthleft = 550;
		int widthright = 1140-widthleft;

		HBox left = new HBox();
		left.setMaxWidth(widthleft);
		left.setMinWidth(widthleft);
		left.setSpacing(spacing);

		HBox playerlist = createPlayerList();
		HBox phase = createPhaseList();

		left.getChildren().addAll(playerlist,phase);

		HBox right = new HBox();
		right.setSpacing(spacing);
		right.setAlignment(Pos.CENTER_RIGHT);
		right.setMaxWidth(widthright);
		right.setMinWidth(widthright);

		StopActionButton = new CustomButton("stopAction",this);
		actionButton = new CustomButton("action",this);
		playButton = new CustomButton("play",this);
		nextPhase = new CustomButton("phase",this);

		right.getChildren().addAll(StopActionButton.getButton(),actionButton.getButton(),playButton.getButton(),nextPhase.getButton());
		playerBalk.getChildren().addAll(left,right);
	}



	private HBox createPlayerList(){
		HBox hbox = new HBox();
		hbox.setMinHeight(36);
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
		hbox.setPadding(new Insets(0,10,0,0));
		hbox.setAlignment(Pos.CENTER);
		HBox phase = createPlayerBox("CURRENT PHASE");
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
}
