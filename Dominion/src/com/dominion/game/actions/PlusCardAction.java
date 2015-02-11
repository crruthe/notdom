package com.dominion.game.actions;

import com.dominion.game.GameState;


public class PlusCardAction implements CardAction {
	private int numCards;

	public PlusCardAction(int numCards) {
		this.numCards = numCards;
	}
	
	@Override
	public void execute(GameState state) {
		for (int i = 0; i < numCards; i++) {
			state.getCurrentPlayer().drawCardToHand();
		}
	}

	public int getNumCards() {
		return numCards;
	}
}
