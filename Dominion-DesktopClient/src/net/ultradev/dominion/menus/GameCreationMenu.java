package net.ultradev.dominion.menus;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import net.ultradev.domininion.GUIUtils.GUtils;
import net.ultradev.dominion.DominionGUIMain;
import net.ultradev.dominion.GameServer;
import net.ultradev.dominion.game.GameConfig;
import net.ultradev.dominion.game.local.LocalGame;
import net.ultradev.dominion.specialScreens.PlayerConfirm;

public class GameCreationMenu {

	private BorderPane root;
	private TextField[] listInput;
	private GUtils utils = new GUtils();
	private ComboBox<String> setlist;


	public GameCreationMenu(){
		listInput = new TextField[4];
		createGameCreationMenu();
	}

	public BorderPane getRoot(){
		return root;
	}

	private void createGameCreationMenu(){

		root = new BorderPane();
		root.setId("Menu");

		VBox container = labelBox();
		container.setAlignment(Pos.CENTER);



		root.setCenter(container);
		HBox buttonContainer = new HBox();
		buttonContainer.setAlignment(Pos.CENTER);
		buttonContainer.setPadding(new Insets(0,0,20,0));

		Button createGameBtn = utils.CreateButton("Create Game", 300, 100);
		createGameBtn.setAlignment(Pos.CENTER);

		createGameBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            	GameServer gs = new GameServer();
            	gs.getUtils().setDebugging(true);
                LocalGame localGame = gs.getGameManager().createLocalGame(null);

                for(int i=0;i<4;i++){
                	if(!listInput[i].getText().equals(""))
                	{
                		localGame.addPlayer(listInput[i].getText());
                	}
                }
                String chosenString = setlist.getValue();
                localGame.getConfig().setCardset(chosenString);
                localGame.start();

                PlayerConfirm pc = new PlayerConfirm(localGame,true);

            	DominionGUIMain.setRoot(pc.getRoot());

            }
        });
		buttonContainer.getChildren().add(createGameBtn);
		root.setBottom(buttonContainer);
	}

	private VBox labelBox(){
		VBox labelBox = new VBox();
		labelBox.setSpacing(20);
		for(int i=0;i<4;i++){
			HBox hbox = utils.createCenterHBox("");
			hbox.setSpacing(60);
			Text playerLabel = createPlayerLabel(i);
			TextField textfield = createTextField();
			textfield.setPrefWidth(150);
			listInput[i]=textfield;
			hbox.getChildren().addAll(playerLabel,textfield );
			labelBox.getChildren().add(hbox);
		}



		HBox setlistBox = createSetList();
		labelBox.getChildren().add(setlistBox);

		return labelBox;
	}

	private HBox createSetList(){
		HBox setlistBox = utils.createCenterHBox("");
		setlistBox.setSpacing(20);
		setlist = new ComboBox<String>();
		setlist.setPrefWidth(150);
		for(int i =0; i<GameConfig.CardSet.values().length; i++){
			setlist.getItems().add(GameConfig.CardSet.values()[i].toString());
		}
		setlist.setValue(GameConfig.CardSet.values()[0].toString());


		Text listLabel = new Text();
		listLabel.setText("Choose a set");
		listLabel.getStyleClass().add("creationMenuLabel");
		setlistBox.getChildren().addAll(listLabel,setlist);
		return setlistBox;
	}

	private Text createPlayerLabel(int index){
		Text playerLabel = new Text();
		String pLabel = "Player "+(index+1);
		playerLabel.setText(pLabel);
		playerLabel.getStyleClass().add("creationMenuLabel");


		return playerLabel;
	}

	private TextField createTextField(){
		TextField textField = new TextField();

		return textField;
	}



}
