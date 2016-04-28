package net.ultradev.dominion;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import net.ultradev.dominion.game.Turn;
import net.ultradev.dominion.gameGUI.GUtils;

public class GameTopMenu {
	private GUtils utils = new GUtils();
	private Turn turn;
	private HBox menu;
	private Text actionCounterText;
	private Text buyCounterText;
	private Text coinsText;

	public GameTopMenu(Turn turn){
		this.turn = turn;
		loadCounterText();
		createMenu();
	}
	public HBox getMenu(){
		return menu;
	}

	private void createMenu(){
		menu = createGameMenuContainer();
		HBox titleBox = createTitleBox();
		HBox counterBox = createCounterBox();
		//TODO dropdown menu maken met https://docs.oracle.com/javafx/2/ui_controls/menu_controls.htm

		HBox menuBox = new HBox();
		menuBox.setAlignment(Pos.CENTER);
		Text placeholder = new Text("Menu");

		menuBox.getChildren().add(placeholder);
		menuBox.setPrefWidth(100);


		menu.getChildren().addAll(titleBox, counterBox, menuBox);

	}

	private HBox createGameMenuContainer(){
		HBox menuContainer = utils.createCenterHBox("GameMenu");
		menuContainer.setFillHeight(true);
		menuContainer.setPrefHeight(60);
		return menuContainer;
	}

	private HBox createTitleBox(){
		HBox titleBox = new HBox();
		titleBox.setAlignment(Pos.CENTER_LEFT);

		Text title = new Text("Dominion");
		title.setId("Title");

		titleBox.getChildren().add(title);
		titleBox.setPrefWidth(100);
		return titleBox;

	}

	private HBox createCounterBox(){
		HBox counterBox = utils.createCenterHBox("");
		counterBox.setPrefWidth(900);

		VBox actionCounter = createCounter("ACTION",actionCounterText);
		VBox buyCounter = createCounter("BUYS",buyCounterText);
		VBox coinCounter = createCounter("COINS",coinsText);

		counterBox.getChildren().addAll(actionCounter,buyCounter,coinCounter);
		return counterBox;
	}

	private VBox createCounter(String text, Text counterText){
		VBox counter = new VBox();
		counter.setAlignment(Pos.CENTER);
		counter.setPrefWidth(100);
		counter.setFillWidth(true);

		Text label = new Text();
		label.setText(text);
		label.setId("label");

		counter.getChildren().addAll(label,counterText);
		return counter;
	}

	private void loadCounterText(){
		actionCounterText = createText(turn.getActions());
		buyCounterText = createText(turn.getBuys());
		coinsText = createText(turn.getBuypower());
	}
	private Text createText(int number){
		Text text = new Text();
		text.setText(Integer.toString(number));
		text.setId("label");;
		return text;
	}
}
