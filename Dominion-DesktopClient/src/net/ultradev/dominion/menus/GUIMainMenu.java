package net.ultradev.dominion.menus;


import javafx.event.*;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import net.ultradev.domininion.GUIUtils.GUtils;
import net.ultradev.dominion.DominionGUIMain;




public class GUIMainMenu {
	private BorderPane root;
	private Button localGameBtn;
	private GUtils utils = new GUtils();
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
		center.setSpacing(10);
		center.setAlignment(Pos.CENTER);

		localGameBtn = utils.CreateButton("Local Game",width,height);
		localGameBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	LocalGameMenu menu = new LocalGameMenu();
            	DominionGUIMain.setRoot(menu.getRoot());
            }
        });

		center.getChildren().add(localGameBtn);
		root.setCenter(center);

		HBox bottom = new HBox();
		bottom.setAlignment(Pos.CENTER_RIGHT);
		Button exitBtn = functions.createExit();
		bottom.getChildren().add(exitBtn);
		root.setBottom(bottom);
	}







}
