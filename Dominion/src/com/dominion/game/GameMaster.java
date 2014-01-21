package com.dominion.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class GameMaster {
	// Singleton with double-checked locking
	private volatile static GameMaster instance;
	
	private GameMaster() {}
	
	public static GameMaster getInstance() {
		if (instance == null) {
			synchronized (GameMaster.class) {
				if (instance == null) {
					instance = new GameMaster();
				}
			}
		}
		return instance;
	}
	// End Singleton
	
	
	private final ArrayList<Player> players = new ArrayList<Player>();
	private final GameBoard gameBoard = new GameBoard();
	
	public void addPlayer(Player player) {
		players.add(player);
	}
	
	/**
	 * Performs all the actions to start the game.
	 * Provides the main game loop.  
	 */
	public void startGame() {
		if (players.size() < 2) {
			throw new RuntimeException("not enough players to start");
		} else if (players.size() > 4) {
			throw new RuntimeException("too many players");
		}

		setupGameBoard();
		
		setupAllPlayers();
		randomisePlayers();
		
		boolean gameEnded = false;
		
		// Track the turn of current player
		int currentPlayerIndex = 0;
		Player currentPlayer;
		
		// Main game loop
		while (!gameEnded) {
			// Determine the next player to have a turn
			currentPlayer = players.get(currentPlayerIndex);
			
			currentPlayer.getPlayerInterface().updateGameBoard();
			
			currentPlayer.actionPhase();
			currentPlayer.buyPhase();
			currentPlayer.cleanUpPhase();
			
			
			gameEnded = hasGameEnded();
			
			// Increase the next player index and return if to the 
			// first player once everyone has had a turn
			currentPlayerIndex = (currentPlayerIndex + 1) % players.size();			
		}
		
		for (Player player : players) {
			player.endGamePhase();
			System.out.println(player.getPlayerInterface().getPlayerName() + " - " + player.countVictoryPoints());
		}
	}
		
	private void setupGameBoard() {
		gameBoard.setup(players.size());
	}

	/**
	 * The game ends at the end of any player’s turn when either:
	 * 1) the Supply pile of Province cards is empty or
	 * 2) any 3 Supply piles are empty.
	 * 
	 * @return whether the game should end
	 */
	private boolean hasGameEnded() {
		int numberOfEmptySupplies = 0;
		
		if (gameBoard.getProvinceStackSize() == 0) {
			return true;
		}
		
		if (gameBoard.getCurseStackSize() == 0) { 
			numberOfEmptySupplies++;
		}
		
		if (gameBoard.getDuchyStackSize() == 0) { 
			numberOfEmptySupplies++;
		}
		
		if (gameBoard.getEstateStackSize() == 0) { 
			numberOfEmptySupplies++;
		}
		
		for (Integer stackSize : gameBoard.getKingdomCards().values()) {
			if (stackSize == 0) {
				numberOfEmptySupplies++;				
			}
		}
		
		return (numberOfEmptySupplies >= 3);
	}

	private void setupAllPlayers() {
		for (Player player : players) {
			player.setup();
			player.getPlayerInterface().setGameBoard(gameBoard);
		}
	}
	
	/**
	 * Randomises the turn order, which also provides the starting player 
	 */
	private void randomisePlayers() {
		Collections.shuffle(players);
	}
	
	public Collection<Player> getPlayers() {
		return players;
	}
	
	public GameBoard getGameBoard() {
		return gameBoard;
	}
}
