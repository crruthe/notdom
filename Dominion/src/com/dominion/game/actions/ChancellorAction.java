package com.dominion.game.actions;


public class ChancellorAction extends CardAction {
	@Override
	public void execute() {
		if (player.getPlayerInterface().wantsToPutDeckInDiscard()) {
			player.moveCardDeckToDiscardPile();
		}
	}	
}
