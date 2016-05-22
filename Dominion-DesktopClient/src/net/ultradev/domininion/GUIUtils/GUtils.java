package net.ultradev.domininion.GUIUtils;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class GUtils {

	public Button CreateButton(String text,double width,double height){
		Button button = new Button();
		button.setText(text);
		button.setPrefSize(width, height);
		return button;

	}


	public Button createExit(){
		Button exitBtn = CreateButton("Exit",75,50);
		exitBtn.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	                Platform.exit();
	            }
	        });
		return exitBtn;

	}

	public HBox createCenterHBox(String id){
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER);
		if(!id.equals("")){
			hbox.setId(id);
		}
		return hbox;
	}


}


