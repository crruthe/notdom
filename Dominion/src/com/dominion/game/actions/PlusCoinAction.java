package com.dominion.game.actions;

import com.dominion.game.GameState;


public class PlusCoinAction implements CardAction {
	private int coins;

	public PlusCoinAction(int coins) {
		this.coins = coins;
	}
	
	@Override
	public void execute(GameState state) {
		state.getTurnState().incrementCoins(coins);		
	}

	public int getCoins() {
		return coins;
	}	
}
