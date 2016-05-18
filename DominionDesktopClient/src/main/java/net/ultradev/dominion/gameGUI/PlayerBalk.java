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
import net.ultradev.dominion.game.card.action.Action;
import net.ultradev.dominion.game.card.action.actions.RemoveCardAction;
import net.ultradev.dominion.game.player.Player;

public class PlayerBalk {
	private HBox playerBalk;
	private List<Player> players;
	private Player currentPlayer;
	private Turn turn;
	private Circle[] circlePhases;
	private GUIGame parent;

	private Button nextPhase;
	private CustomButton playButton;
	private CustomButton discardButton;

	public PlayerBalk(GUIGame parent){
		turn = parent.getTurn();
		this.players = turn.getGame().getPlayers();
		this.currentPlayer = turn.getPlayer();
		this.parent = parent;
		createPlayerBalk();
	}

	public HBox getPlayerBalk(){
		return playerBalk;
	}

	public CustomButton getPlayButton(){
		return playButton;
	}
	public CustomButton getDiscardButton(){
		return discardButton;
	}

	public void changeDiscardToTrashButton(){
		discardButton.getButton().setText("TRASH CARD");
	}


	public void setOnTreasurePhase(){
		changePhase(0,"GO TO CLEAN-UP PHASE");
	}

	private void changePhase(int index, String text){

		circlePhases[index].setFill(Color.rgb(236, 240, 241));
		circlePhases[index+1].setFill(Color.rgb(241, 196, 15));
		nextPhase.setText(text);
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

		discardButton = new CustomButton("DISCARD CARD");
		discardButton.setActive(false);
		discardButton.getButton().setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				for(Action action: parent.getHand().getLastPlayedCard().getActions()){
					if(action instanceof RemoveCardAction){
						((RemoveCardAction) action).selectCard(parent.getTurn(), parent.getHand().getActiveGCard().getSourceCard());
						parent.getHand().getActiveGCard().removeCard();
						System.out.println(parent.getTurn().getBuypower());
					}
				}

			}
		});

		playButton = new CustomButton("PLAY CARD");
		playButton.getButton().setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				try{
					parent.getHand().getActiveGCard().playCard();
				}catch(Exception e){

				}

			}
		});
		nextPhase = new CustomButton("GO TO TREASURE PHASE").getButton();
		nextPhase.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            	switch(parent.getTurn().getPhase().toString()){
            		case "ACTION":
            			turn.endPhase();
            			setOnTreasurePhase();
            			break;
            		case "BUY":
            			turn.endPhase();
            			changePhase(1,"END TURN");

                    	parent.getHand().reloadCards(true);
                    	parent.loadCardsPlayed();
                    	parent.getCardSet().loadRows();
                    	break;
            		default:
            			parent.getCardViewer().close();
                    	PlayerConfirm playerConfirm = new PlayerConfirm(turn.getGame(),false);
                    	DominionGUIMain.setRoot(playerConfirm.getRoot());
                    	break;
            	}
            	if(parent.getHand().getActiveGCard() != null){
            		getPlayButton().setActive(turn.canPlay(turn.getPhase(), parent.getHand().getActiveGCard().getTitle()));
            	}


            }
        });


		right.getChildren().addAll(discardButton.getButton(),playButton.getButton(),nextPhase);
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
