package com.dominion.game.actions;

import com.dominion.game.GameState;


public class ChancellorAction implements CardAction {
	@Override
	public void execute(GameState state) {
		if (state.getCurrentPlayer().wantsToPutDeckInDiscard()) {
			state.getCurrentPlayer().discardEntireCardDeck();
		}
	}	
}
