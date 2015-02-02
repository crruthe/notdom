package com.dominion.game.actions;


public class PlusCoinAction extends CardAction {
	private int coins;

	public PlusCoinAction(int coins) {
		this.coins = coins;
	}
	
	@Override
	public void execute() {
		player.incrementCoins(coins);		
	}

	public int getCoins() {
		return coins;
	}	
}
