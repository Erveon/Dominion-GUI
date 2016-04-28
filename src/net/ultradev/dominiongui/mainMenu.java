package net.ultradev.dominiongui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class mainMenu {
	
	private static int menuButtonHeight = 300;
	private static int menuButtonWeidth = 75;
	
	public static Scene getMainMenu(){
		BorderPane root = new BorderPane();
		
		
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25,25,25,25));
		
		Button playLocalBtn = new Button();
		playLocalBtn.setText("Play local");
		playLocalBtn.setPrefSize(menuButtonHeight, menuButtonWeidth);
		
		
		grid.add(playLocalBtn, 0, 0);
		
		Button playOnlineBtn = new Button();
		playOnlineBtn.setText("Play Online");
		playOnlineBtn.setPrefSize(menuButtonHeight, menuButtonWeidth);
		grid.add(playOnlineBtn, 0, 1);
		
		Button optionsBtn = new Button();
		optionsBtn.setText("Options");
		optionsBtn.setPrefSize(menuButtonHeight, menuButtonWeidth);
		grid.add(optionsBtn, 0, 2);
		
		root.setCenter(grid);
				
		Button exitBtn = new Button();
		
		exitBtn.setPrefSize(200, 50);
		exitBtn.setText("Exit");
		exitBtn.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e){
				System.exit(0); //TODO Afsluiten verbeteren
			}
		});
		
		HBox bottomBox = new HBox();
		bottomBox.getChildren().add(exitBtn);
		bottomBox.setAlignment(Pos.BOTTOM_RIGHT);
		bottomBox.setPadding(new Insets(10,10,10,10));
		root.setBottom(bottomBox);
		
		
				
		Scene scene = new Scene(root,600,500);
		root.prefHeight(scene.getHeight());
		root.prefWidth(scene.getWidth());
		scene.getStylesheets().add(DominionMainGUI.class.getResource("GUIStyle.css").toExternalForm());
		return scene;
		
		
	}
	
	
}
