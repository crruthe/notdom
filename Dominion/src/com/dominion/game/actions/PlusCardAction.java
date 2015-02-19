package com.dominion.game.actions;

import com.dominion.game.GameState;
import com.dominion.game.Player;


public class PlusCardAction implements CardAction {
	private int numCards;
	private Player player;

	public PlusCardAction(Player player, int numCards) {
		this.numCards = numCards;
		this.player = player;
	}
	
	public PlusCardAction(int numCards) {
		this.numCards = numCards;
	}
	
	@Override
	public void execute(GameState state) {
		if (player == null){
			player = state.getCurrentPlayer();
		}
		
		for (int i = 0; i < numCards; i++) {
			player.drawCardToHand();
		}
	}

	public int getNumCards() {
		return numCards;
	}
}
