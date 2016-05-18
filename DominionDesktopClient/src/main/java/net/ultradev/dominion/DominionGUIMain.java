package net.ultradev.dominion;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import net.ultradev.dominion.gameGUI.GUIMainMenu;


public class DominionGUIMain extends Application {
	public static Stage stage;

	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;
		stage.setTitle("Dominion");
		stage.getIcons().add(new Image("file:Images/UltraDevLogo.png"));
		stage.setMinHeight(960);
		stage.setMinWidth(1280);
		GUIMainMenu menu = new GUIMainMenu();
		setRoot(menu.getRoot());
	}

	public static void setRoot(Pane root){
		Scene scene = new Scene(root,1280,960);
		stage.setScene(scene);
		scene.getStylesheets().add (DominionGUIMain.class.getResource("MainStyle.css").toExternalForm());
		stage.show();
	}


	public static void main(String[] args) {
		launch(args);
	}
}
