package com.dominion.game;

public class TurnState {
	private int numberOfActions;
	private int numberOfBuys;
	private int totalCoins;	
	
	public TurnState() {
		reset();
	}
	
	public void decrementActions() {
		numberOfActions--;
	}
	
	public void decrementBuys() {
		numberOfBuys--;
	}
	
	public void decrementCoins(int coins) {
		totalCoins -= coins;
	}
	
	/**
	 * @return the numberOfActions
	 */
	public int getNumberOfActions() {
		return numberOfActions;
	}
	
	/**
	 * @return the numberOfBuys
	 */
	public int getNumberOfBuys() {
		return numberOfBuys;
	}
	
	/**
	 * @return the totalCoins
	 */
	public int getTotalCoins() {
		return totalCoins;
	}

	public void incrementActions(int amount) {
		numberOfActions += amount;
	}
	
	public void incrementBuys(int amount) {
		numberOfBuys += amount;
	}

	public void incrementCoins(int amount) {
		totalCoins += amount;
	}
	
	public void reset() {
		numberOfBuys = 1;
		numberOfActions = 1;
		totalCoins = 0;			
	}
	
	public void zeroActions() {
		numberOfActions = 0;
	}

	public void zeroBuys() {
		numberOfBuys = 0;
	}	
}
