package com.dominion.game.actions;

import com.dominion.game.cards.Card;

public class CourtyardAction extends CardAction {
	@Override
	public void execute() {
		Card card = null;
		
		do {
			card = player.getCardFromHandToPutInDeck();
		} while (card == null);
		
		player.moveCardFromHandToDeck(card);
	}	
}
