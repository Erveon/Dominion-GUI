package net.ultradev.dominion.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.sf.json.JSONObject;
import net.ultradev.dominion.GameServer;
import net.ultradev.dominion.game.Turn.Phase;
import net.ultradev.dominion.game.player.Player;

// Overhead for local & multiplayer games
// Abstract in case methods differ per type
public abstract class Game {

	public static enum State { SETUP, STARTED };
	
	private List<Player> players;
	private State state;
	
	//In case there is a tie, this will determine who wins (least amount of turns)
	private Player started;

	private Turn turn;
	private GameConfig config;
	private Board board = null;
	private GameServer gs;
	
	public Game(GameServer gs) {
		this.gs = gs;
		this.state = State.SETUP;
		this.players = new ArrayList<>();
		this.config = new GameConfig(this);
		this.board = new Board(this);
	}
	
	public GameServer getGameServer() {
		return gs;
	}
	
	public State getState() {
		return state;
	}
	
	public boolean hasStarted() {
		return state.equals(State.STARTED);
	}
	
	public Board getBoard() {
		return board;
	}
	
	public GameConfig getConfig() {
		return config;
	}

	/**
	 * Start the game. Done with an ajax call after all the settings have been configured.
	 * Player null means a random player starts
	 */
	public void start() {
		start(getPlayers());
	}
	
	/**
	 * Set variables when the game has been configured
	 */
	public void init() {
		getBoard().initSupplies();
		// .setup() for every player
		getPlayers().forEach(Player::setup);
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public Player getWhoStarted() {
		return this.started;
	}
	
	public Player getWinner() {
		Player winner = getPlayers().get(0);
		for(Player p : getPlayers()) {
			if(!p.equals(winner)) {
				int pVic = p.getVictoryPoints();
				int wVic = winner.getVictoryPoints();
				if(pVic > wVic) {
					winner = p;
				} else if(pVic == wVic) {
					// If they have the same amount of points,
					// Check for the one with the least rounds
					if(p.getRounds() < winner.getRounds()) {
						winner = p;
					}
				}
			}
		}
		return winner;
	}
	
	/**
	 * Loser of the previous game starts the next one
	 * Player(s) to start is an array because a tie is possible
	 * @param p Eligible to start
	 */
	public void start(List<Player> p) {
		getGameServer().getUtils().debug("A game has been started");
		init();
		Player starter = p.get(new Random().nextInt(p.size()));
		this.started = starter;
		this.state = State.STARTED;
		setTurn(new Turn(this, starter));
	}
	
	public Turn getTurn() {
		return turn;
	}
	
	public void endTurn() {
		getTurn().getPlayer().increaseRounds();
		if(getBoard().hasEndCondition()) {
			endGame();
		} else {
			setTurn(getTurn().getNextTurn());
		}
	}
	
	public void endPhase() {
		getTurn().endPhase();
		if(getTurn().getPhase().equals(Phase.CLEANUP)) {
			endTurn();
		}
	}
	
	public void setTurn(Turn turn) {
		this.turn = turn;
	}
	
	public void endGame() {
		//Player winner = getWinner();
		//TODO win!
	}
	
	public void addPlayer(String name) {
		if(getPlayerByName(name) == null) {
			getPlayers().add(new Player(this, name));
			getGameServer().getUtils().debug("A player named " + name + " has been added to the game");
		}
	}
	
	public Player getPlayerByName(String name) {
		for(Player p : getPlayers()) {
			if(p.getDisplayname().equalsIgnoreCase(name)) {
				return p;
			}
		}
		return null;
	}
	
	public List<JSONObject> getPlayersAsJson() {
		List<JSONObject> objs = new ArrayList<>();
		players.forEach(player -> objs.add(player.getAsJson()));
		return objs;
	}
	
	public JSONObject getAsJson() {
		return new JSONObject()
				.accumulate("players", getPlayersAsJson())
				.accumulate("board", getBoard().getAsJson())
				.accumulate("turn", getTurn() == null ? "null" : getTurn().getAsJson());
	}

}
