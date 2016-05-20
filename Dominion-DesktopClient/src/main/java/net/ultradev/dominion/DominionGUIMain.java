package net.ultradev.dominion;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class DominionGUIMain extends Application {
	public static Stage stage;

	@Override
	public void start(Stage primaryStage) {
		int height = 960;
		int width = 1280;
		stage = primaryStage;
		stage.setTitle("Dominion");
		stage.getIcons().add(new Image("file:Images/UltraDevLogo.png"));
		stage.setMinHeight(height);
		stage.setMinWidth(width);
		stage.setHeight(height);
		stage.setWidth(width);
		GUIMainMenu menu = new GUIMainMenu();
		setRoot(menu.getRoot());
	}

	public static void setRoot(Pane root){
		Scene scene = new Scene(root,stage.getWidth(),stage.getHeight());
		stage.setScene(scene);
		scene.getStylesheets().add (DominionGUIMain.class.getResource("MainStyle.css").toExternalForm());
		stage.show();
	}


	public static void main(String[] args) {
		launch(args);
	}
}
