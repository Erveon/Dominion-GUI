package net.ultradev.dominion.specialScreens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.sf.json.JSONObject;
import net.ultradev.dominion.Buttons.ActionButton;
import net.ultradev.dominion.game.Turn;
import net.ultradev.dominion.game.player.Player;
import net.ultradev.dominion.gameGUI.GameTopMenu;
import net.ultradev.dominion.gameGUI.Hand;

public class CardSelection {
	private BorderPane root;
	private Player player;
	private Hand hand;

	public CardSelection(Turn turn,JSONObject response){

		this.player = turn.getGame().getPlayerByName(response.getString("player"));
		createCardSelection(turn, response);
	}

	public BorderPane getRoot(){
		return root;
	}

	private void createCardSelection(Turn turn, JSONObject response) {
		root = new BorderPane();

		root.setId("Game");
		root.setPrefWidth(1280);

		GameTopMenu topMenu = new GameTopMenu(turn, null);
		root.setTop(topMenu.getMenu());

		HBox center = new HBox();
		center.setId("center");
		center.setAlignment(Pos.CENTER);
		center.setPadding(new Insets(10,0,10,0));
		ActionButton actionButton = new ActionButton(turn, response);

		center.getChildren().addAll(actionButton.getButton());
		root.setCenter(center);

		hand = new Hand(player, actionButton);

		root.setBottom(hand.getHand());
		actionButton.setHand(hand);
		actionButton.setAction(response);
	}
}
