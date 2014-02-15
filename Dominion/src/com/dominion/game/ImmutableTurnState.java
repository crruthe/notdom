package com.dominion.game;

final public class ImmutableTurnState {
	final private int numberOfBuys;
	final private int numberOfActions;
	final private int totalCoins;
	
	public ImmutableTurnState(TurnState turnState) {
		this.numberOfBuys = turnState.getNumberOfBuys();
		this.numberOfActions = turnState.getNumberOfActions();
		this.totalCoins = turnState.getTotalCoins();
	}

	public int getNumberOfBuys() {
		return numberOfBuys;
	}

	public int getNumberOfActions() {
		return numberOfActions;
	}

	public int getTotalCoins() {
		return totalCoins;
	}	
}
