package com.dominion.game;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import com.dominion.game.cards.basic.CopperCard;
import com.dominion.game.cards.basic.EstateCard;
import com.dominion.game.cards.basic.ProvinceCard;


public class GameMaster {
	private final static int NUM_ESTATE_SETUP = 3;
	private final static int NUM_COPPER_SETUP = 7;
	
	private final LinkedList<Player> players = new LinkedList<Player>();
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
		Player currentPlayer = null;
		
		// Main game loop
		while (!gameEnded) {			
			// Determine the next player in the queue to and remove from the list
			// i.e. players = players - currentPlayers = otherPlayers
			currentPlayer = players.remove();
			
			currentPlayer.setOtherPlayers(players);
			currentPlayer.playTurn();			
			
			gameEnded = hasGameEnded();

			// Add current player to the end of the queue
			players.add(currentPlayer);			
		}
		
		// Tally the victory points
		for (Player player : players) {
			
			// Move all the cards into the card deck for tally
			player.discardHand();
			player.moveDiscardPileToCardDeck();
			
			int count = player.countVictoryPointsInCardDeck();
			player.actionEndGameScore(count);
			System.out.println("Score: " + count);			
		}
	}
		
	private void setupGameBoard() {
		gameBoard.setup(players.size());
	}

	/**
	 * The game ends at the end of any player�s turn when either:
	 * 1) the Supply pile of Province cards is empty or
	 * 2) any 3 Supply piles are empty.
	 * 
	 * @return whether the game should end
	 */
	private boolean hasGameEnded() {
		if (gameBoard.isStackEmpty(ProvinceCard.NAME)) {
			return true;
		}

		if (gameBoard.countNumberOfEmptyStacks() >= 3) {
			return true;
		}
		
		return false;
	}

	private void setupAllPlayers() {
		for (Player player : players) {
			player.setGameBoard(gameBoard);
			
			buildDeckForPlayer(player);
			player.drawNewHand();
		}
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
			player.gainCardFromSupply(EstateCard.NAME);
		}
		
		for (int i = 0; i < NUM_COPPER_SETUP; i++) {
			player.gainCardFromSupply(CopperCard.NAME);
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
