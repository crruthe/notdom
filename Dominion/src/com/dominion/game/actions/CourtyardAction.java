package com.dominion.game.actions;

import com.dominion.game.GameState;
import com.dominion.game.cards.Card;

public class CourtyardAction implements CardAction {
	@Override
	public void execute(GameState state) {
		// No cards in the hand to return to the deck
		if (state.getCurrentPlayer().getHandSize() == 0) {
			return;
		}
		
		// The player must return a card
		Card card = null;
		while (card == null) {
			card = state.getCurrentPlayer().getCardToPutOnDeck();
		}
		
		state.getCurrentPlayer().moveCardFromHandToDeck(card);
	}	
}
