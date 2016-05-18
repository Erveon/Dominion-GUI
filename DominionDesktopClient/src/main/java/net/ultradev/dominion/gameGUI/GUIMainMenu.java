package net.ultradev.dominion.gameGUI;


import javafx.event.*;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import net.ultradev.dominion.DominionGUIMain;
import net.ultradev.dominion.LocalGameMenu;



public class GUIMainMenu {
	private BorderPane root;
	private Button localGameBtn;
	private Button onlineGameBtn;
	public GUIMainMenu(){
		createMainMenu();

	}

	public BorderPane getRoot(){
		createMainMenu();
		return root;
	}

	private void createMainMenu(){
		GUtils functions = new GUtils();
		double width = 300;
		double height = 100;
		root = new BorderPane();
		root.setId("Menu");
		VBox center = new VBox();
		center.setAlignment(Pos.CENTER);




		localGameBtn = new CustomButton("Local Game",width,height).getButton();
		localGameBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	LocalGameMenu menu = new LocalGameMenu();
            	DominionGUIMain.setRoot(menu.getRoot());
            }
        });

		onlineGameBtn = new CustomButton("Online Game",width,height).getButton();
		onlineGameBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	onlineGameBtn.setText("NOT AVAILABLE YET");
            }
        });

		Button optionsBtn =  new CustomButton("Options",width,height).getButton();
		center.getChildren().addAll(localGameBtn,onlineGameBtn,optionsBtn);
		root.setCenter(center);

		HBox bottom = new HBox();
		bottom.setAlignment(Pos.CENTER_RIGHT);
		Button exitBtn = functions.createExit();
		bottom.getChildren().add(exitBtn);
		root.setBottom(bottom);
	}







}
