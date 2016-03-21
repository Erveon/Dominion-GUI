package net.ultradev.dominiongui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.application.*;
import javafx.stage.*;

public class DominionMainGUI extends Application {

	@Override
	public void start(Stage primaryStage){
		primaryStage.setTitle("Dominion");	
		
		BorderPane root = new BorderPane();
		
		
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25,25,25,25));
		
		Button playLocalBtn = new Button();
		playLocalBtn.setText("Play local");
		playLocalBtn.setPrefWidth(100);
		grid.add(playLocalBtn, 0, 0);
		
		Button playOnlineBtn = new Button();
		playOnlineBtn.setText("Play Online");
		playOnlineBtn.setPrefWidth(100);
		grid.add(playOnlineBtn, 0, 1);
		
		Button optionsBtn = new Button();
		optionsBtn.setText("Options");
		optionsBtn.setPrefWidth(100);
		grid.add(optionsBtn, 0, 2);
		
		root.setCenter(grid);
				
		Button exitBtn = new Button();
		exitBtn.setPrefWidth(100);
		exitBtn.setText("Exit");
		
		HBox bottomBox = new HBox();
		bottomBox.getChildren().add(exitBtn);
		bottomBox.setAlignment(Pos.BOTTOM_RIGHT);
		bottomBox.setPadding(new Insets(10,10,10,10));
		root.setBottom(bottomBox);
		
		
				
		Scene scene = new Scene(root,600,500);
		root.prefHeight(scene.getHeight());
		root.prefWidth(scene.getWidth());
		primaryStage.setScene(scene);
		scene.getStylesheets().add(DominionMainGUI.class.getResource("GUIStyle.css").toExternalForm());
		
		primaryStage.show();		
		
		
		
	}
	public static void main(String[] args){
		launch(args);
	}
}
