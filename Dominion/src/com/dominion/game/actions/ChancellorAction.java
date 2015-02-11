package com.dominion.game.actions;

import com.dominion.game.Player;


public class ChancellorAction implements CardAction {
	@Override
	public void execute(Player player) {
		if (player.wantsToPutDeckInDiscard()) {
			player.moveCardDeckToDiscardPile();
		}
	}	
}
