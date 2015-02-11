package com.dominion.game;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.dominion.game.cards.basic.CopperCard;
import com.dominion.game.cards.basic.EstateCard;
import com.dominion.game.cards.basic.ProvinceCard;

/**
 * GameState is a container for everything that makes up a current state.
 * The game state will be invoked by Actions.
 */
public class GameState {

	private final static int NUM_COPPER_SETUP = 7;
	private final static int NUM_ESTATE_SETUP = 3;		

	private Player currentPlayer;
	private final GameBoard gameBoard = new GameBoard();
	private final LinkedList<Player> players = new LinkedList<Player>();	
	private TurnState turnState = new TurnState();
	
	public void addPlayer(Player player) {
		players.add(player);
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public GameBoard getGameBoard() {
		return gameBoard;
	}	

	public List<Player> getPlayers() {
		return players;
	}
		
	public TurnState getTurnState() {
		return turnState;
	}
	
	/**
	 * The game ends at the end of any player's turn when either:
	 * 1) the Supply pile of Province cards is empty or
	 * 2) any 3 Supply piles are empty.
	 * 
	 * @return whether the game should end
	 */
	public boolean hasGameEnded() {
		if (gameBoard.isStackEmpty(ProvinceCard.NAME)) {
			return true;
		}

		if (gameBoard.countNumberOfEmptyStacks() >= 3) {
			return true;
		}
		
		return false;
	}
	
	public void initialise() {
		setupGameBoard();
		setupAllPlayers();
		randomisePlayers();
		
		// This will setup the current player
		rotatePlayers();
	}
	
	public void rotatePlayers() {
		if (currentPlayer != null) {
			players.addLast(currentPlayer);
		}
		currentPlayer = players.removeFirst();
	}
	
	/**
	 * Each player starts the game with the same cards:
	 * 7 coppers
	 * 3 estates
	 * 
	 * Each player shuffles these cards and places them (their Deck)
	 * face-down in their play area (the area near them on the table).
	 */
	private void buildDeckForPlayer(Player player) {
		for (int i = 0; i < NUM_ESTATE_SETUP; i++) {
			player.addCardToCardDeck(new EstateCard());
		}
		
		for (int i = 0; i < NUM_COPPER_SETUP; i++) {
			player.addCardToCardDeck(new CopperCard());
		}
	}
	
	/**
	 * Randomises the turn order, which also provides the starting player 
	 */
	private void randomisePlayers() {
		Collections.shuffle(players);
	}
	
	/**
	 * Build the starting deck and draw a new hand for each player
	 */
	private void setupAllPlayers() {
		for (Player player : players) {
			buildDeckForPlayer(player);
			player.drawNewHand();
		}
	}
	
	/**
	 * Set up the game board with all the supply stacks. 
	 * Number of players will affect the supply sizes.
	 */
	private void setupGameBoard() {
		gameBoard.setup(players.size());
	}	
}
