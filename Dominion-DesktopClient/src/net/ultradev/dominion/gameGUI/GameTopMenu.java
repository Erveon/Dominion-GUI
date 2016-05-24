package net.ultradev.dominion.gameGUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import net.ultradev.domininion.GUIUtils.GUtils;
import net.ultradev.dominion.DominionGUIMain;
import net.ultradev.dominion.game.Turn;
import net.ultradev.dominion.game.player.Player.Pile;

public class GameTopMenu {
	private GUtils utils = new GUtils();
	private Turn turn;
	private HBox topMenu;
	private Text actionCounterText;
	private Text buyCounterText;
	private Text coinsText;
	private Text handCounterText;
	private GUIGame parent;

	public GameTopMenu(Turn turn, GUIGame parent){
		this.parent = parent;
		this.turn = turn;
		loadCounterText();
		createTopMenu();
	}
	public HBox getMenu(){
		return topMenu;
	}
	public Text getcoinsText(){
		return coinsText;
	}
	public Text getActionCounterText(){
		return actionCounterText;
	}
	public Text getBuyCounterText(){
		return buyCounterText;
	}
	public void reloadCounters(){
		actionCounterText.setText(Integer.toString(turn.getActions()));
		coinsText.setText(Integer.toString(turn.getBuypower()));
		buyCounterText.setText(Integer.toString(turn.getBuys()));
		handCounterText.setText(Integer.toString(turn.getPlayer().getPile(Pile.HAND).size()));
	}

	private void createTopMenu(){
		topMenu = createGameMenuContainer();
		if(parent != null){
			HBox titleBox = createTitleBox();
			HBox counterBox = createCounterBox();
			topMenu.getChildren().addAll(titleBox, counterBox);
		}

		MenuBar menuBar = new MenuBar();
		Menu menu = new Menu("Menu");

		MenuItem exit = new MenuItem("Exit Game");
		exit.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				parent.getCardViewer().close();
				DominionGUIMain.stage.close();
			}
		});

		menu.getItems().add(exit);
		menuBar.getMenus().add(menu);

		topMenu.getChildren().add(menuBar);

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
		VBox cardsHandCounter = createCounter("HAND",handCounterText);

		counterBox.getChildren().addAll(actionCounter,buyCounter,coinCounter,cardsHandCounter);
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
		handCounterText = createText(turn.getPlayer().getPile(Pile.HAND).size());
	}

	private Text createText(int number){
		Text text = new Text();
		text.setText(Integer.toString(number));
		text.setId("label");;
		return text;
	}
}
