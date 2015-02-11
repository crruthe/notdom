package com.dominion.game.actions;

import com.dominion.game.Player;


public class PlusCardAction implements CardAction {
	private int numCards;

	public PlusCardAction(int numCards) {
		this.numCards = numCards;
	}
	
	@Override
	public void execute(Player player) {
		for (int i = 0; i < numCards; i++) {
			player.drawCardToHand();
		}
	}

	public int getNumCards() {
		return numCards;
	}
}
