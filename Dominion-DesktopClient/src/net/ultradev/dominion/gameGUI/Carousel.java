package net.ultradev.dominion.gameGUI;




import java.util.*;

import javafx.event.EventHandler;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;

public class Carousel {
	private Polygon buttonLeft;
	private Polygon buttonRight;
	private HBox box;
	private ArrayList<VBox> cards;
	private int startPosition;

	public Carousel(){
		cards = new ArrayList<VBox>();
		buttonLeft = createButtonLeft();
		buttonRight = createButtonRight();
	}

	public void setCarousel(HBox box, ArrayList<GUICard> Gcards){
		cards.clear();
		for(int i=0;i<Gcards.size();i++){
			cards.add(Gcards.get(i).getCard());
		}
		this.box = box;
		box.getChildren().clear();
		box.getChildren().add(buttonLeft);
		int index = 5;
		if(Gcards.size()<5){
			index = Gcards.size();
		}
		for(int i=0; i<index;i++){
				box.getChildren().add(cards.get(i));
		}

		box.getChildren().add(buttonRight);
	}
	public void setCarouselMini(HBox box, ArrayList<miniCard> Gcards){
		cards.clear();
		for(int i=0;i<Gcards.size();i++){
			cards.add(Gcards.get(i).getCard());
		}
		this.box = box;
		box.getChildren().clear();
		box.getChildren().add(buttonLeft);
		int index = 5;
		if(Gcards.size()<5){
			index = Gcards.size();
		}
		for(int i=0; i<index;i++){
				box.getChildren().add(cards.get(i));
		}

		box.getChildren().add(buttonRight);
	}

	private Polygon createButtonLeft(){
		Polygon button = new Polygon();
		button.getPoints().addAll(new Double[]{
			    0.0, 0.0,
			    -20.0, 20.0,
			    0.0, 40.0 });

		button.setId("CarouselButton");
		button.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent mouseEvent) {
		        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
		        	goLeft();

		        }
		    }
		});
		return button;
	}
	private Polygon createButtonRight(){
		Polygon button = new Polygon();
		button.getPoints().addAll(new Double[]{
			    0.0, 0.0,
			    20.0, 20.0,
			    0.0, 40.0 });
		button.setId("CarouselButton");
		button.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent mouseEvent) {
		        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
		        	goRight();

		        }
		    }
		});
		return button;
	}

	private void goLeft(){
		if(startPosition > 0){
			startPosition--;
			box.getChildren().remove(5);
			box.getChildren().remove(0);
			box.getChildren().add(0, buttonLeft);
			box.getChildren().add(1,cards.get(startPosition));

		}
	}
	private void goRight(){
		if(startPosition+5 <= cards.size() && cards.size()-startPosition>5){
			box.getChildren().remove(1);
			box.getChildren().remove(5);
			box.getChildren().addAll(cards.get(startPosition+5),buttonRight);
			startPosition++;
		}
	}

}
