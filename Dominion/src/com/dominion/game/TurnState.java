package com.dominion.game;

public class TurnState {
	private int numberOfBuys = 1;
	private int numberOfActions = 1;
	private int totalCoins = 0;	
	
	/**
	 * @return the numberOfBuys
	 */
	public int getNumberOfBuys() {
		return numberOfBuys;
	}
	
	/**
	 * @return the numberOfActions
	 */
	public int getNumberOfActions() {
		return numberOfActions;
	}
	
	/**
	 * @return the totalCoins
	 */
	public int getTotalCoins() {
		return totalCoins;
	}
	
	public void incrementBuys(int amount) {
		numberOfBuys += amount;
	}
	
	public void incrementActions(int amount) {
		numberOfActions += amount;
	}

	public void incrementCoins(int amount) {
		totalCoins += amount;
	}
	
	public void decrementBuys() {
		numberOfBuys--;
	}

	public void decrementActions() {
		numberOfActions--;
	}
	
	public void decrementCoins(int coins) {
		totalCoins -= coins;
	}
	
	public void zeroBuys() {
		numberOfBuys = 0;
	}

	public void zeroActions() {
		numberOfActions = 0;
	}	
}
