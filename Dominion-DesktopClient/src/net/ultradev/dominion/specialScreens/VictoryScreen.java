package net.ultradev.dominion.specialScreens;

import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class VictoryScreen {
	private BorderPane root;

	public VictoryScreen(String playername, String points){
		root = new BorderPane();
		createScreen(playername, points);
	}
	public BorderPane getRoot(){
		return root;
	}
	private void createScreen(String playername, String points) {
		root.setId("Menu");

		Text gameOver = new Text("GAME OVER");
		gameOver.setId("gameover");


		VBox center = new VBox();
		center.setAlignment(Pos.CENTER);
		center.setSpacing(20);
		Text winner = new Text("Player " + playername + " has won with " + points  + " points");
		winner.setId("winner");

		center.getChildren().addAll(gameOver, winner);
		root.setCenter(center);
	}
}
