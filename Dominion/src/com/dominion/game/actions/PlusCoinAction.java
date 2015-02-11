package com.dominion.game.actions;

import com.dominion.game.Player;


public class PlusCoinAction implements CardAction {
	private int coins;

	public PlusCoinAction(int coins) {
		this.coins = coins;
	}
	
	@Override
	public void execute(Player player) {
		player.incrementCoins(coins);		
	}

	public int getCoins() {
		return coins;
	}	
}
