package net.ultradev.dominion.game.card;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;
import net.ultradev.dominion.game.card.action.Action;

public class Card {
	
	public enum CardType { ACTION, TREASURE, VICTORY, CURSE }
	
	String name;
	String description;
	int cost;
	List<Action> actions;
	List<String> types;
	CardType type;
	
	public Card(String name, String description, int cost, CardType type) {
		this.name = name;
		this.cost = cost;
		this.type = type;
		this.description = description;
		this.actions = new ArrayList<>();
		this.types = new ArrayList<>();
	}
	
	public Card(String name, String description, int cost) {
		this.name = name;
		this.cost = cost;
		this.type = CardType.ACTION;
		this.description = description;
		this.actions = new ArrayList<>();
		this.types = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}
	
	public int getCost() {
		return cost;
	}
	
	public String getDescription() {
		return description;
	}
	
	public List<Action> getActions() {
		return actions;
	}
	
	public void addAction(Action action) {
		if(action != null) {
			this.actions.add(action);
		}
	}
	
	public CardType getType() {
		return type;
	}
	
	public List<String> getTypes() {
		List<String> types = new ArrayList<>();
		types.add(type.toString());
		types.addAll(this.types);
		return types;
	}
	
	public void addType(String type) {
		this.types.add(type);
	}
	
	public String getTypesFormatted() {
		boolean first = true;
		StringBuilder sb = new StringBuilder();
		for(String s : getTypes()) {
			if(first) {
				sb.append(s);
				first = false;
			} else {
				sb.append(" - " + s);
			}
		}
		return sb.toString();
	}
	
	private List<String> getActionDescriptions() {
		List<String> desc = new ArrayList<>();
		getActions().forEach(action -> desc.add(action.getDescripton()));
		return desc;
	}
	
	public JSONObject getAsJson() {
		return new JSONObject()
				.accumulate("name", getName())
				.accumulate("cost", getCost())
				.accumulate("type", getTypesFormatted())
				.accumulate("description", getDescription())
				.accumulate("actions", getActionDescriptions());
	}

}
