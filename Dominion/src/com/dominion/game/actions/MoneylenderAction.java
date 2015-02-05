package com.dominion.game.actions;

import com.dominion.game.cards.Card;

public class MoneylenderAction extends CardAction {
	@Override
	public void execute() {
		Card card = player.getCopperCardToTrash();
		if (card != null) {
			player.trashCardFromHand(card);
			player.incrementCoins(3);
		}		
	}	
}
