package net.ultradev.dominiongui;


import javafx.application.*;
import javafx.stage.*;

public class DominionMainGUI extends Application {
	@Override
	public void start(Stage primaryStage){
		primaryStage.setTitle("Dominion");	
		primaryStage.setFullScreen(true);
		
		primaryStage.setScene(mainMenu.getMainMenu());
		
		
		primaryStage.show();	
	}
	
	public static void main(String[] args){
		launch(args);
	}
}
