package net.ultradev.dominion.gameGUI;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class GUtils {



	public Button createExit(){
		Button exitBtn = new CustomButton("Exit",75,50).getButton();
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


