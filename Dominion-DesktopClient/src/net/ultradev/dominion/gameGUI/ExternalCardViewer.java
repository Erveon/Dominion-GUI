package net.ultradev.dominion.gameGUI;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.ultradev.dominion.DominionGUIMain;
import net.ultradev.dominion.cardsGUI.GUICard;

public class ExternalCardViewer extends Stage {
	private int height = 357;
	private int width = 238;

	public ExternalCardViewer(){

		this.setTitle("Dominion Card Viewer");
		this.getIcons().add(new Image("/UltraDevLogo.png"));
		this.setMinHeight(height);
		this.setMinWidth(width);
		this.setMaxHeight(height);
		this.setMaxWidth(width);
		this.setX(1600);
		this.setY(250);

		VBox root = new GUICard().getCard();
		setRoot(root);
	}

	public void setRoot(VBox root){
		Scene scene = new Scene(root,220,310);
		this.setScene(scene);
		scene.getStylesheets().add (DominionGUIMain.class.getResource("MainStyle.css").toExternalForm());
		this.show();
	}
}
