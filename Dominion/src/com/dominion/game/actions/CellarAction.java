package com.dominion.game.actions;

import com.dominion.game.GameState;
import com.dominion.game.cards.Card;

public class CellarAction implements CardAction {
	@Override
	public void execute(GameState state) {
		boolean endAction = false;
		int numberDiscardedOfCards = 0;
		
		while (!endAction) {
			Card card = state.getCurrentPlayer().getCardToDiscard();
			
			if (card == null) {
				endAction = true;
			} else {
				state.getCurrentPlayer().discardCardFromHand(card);
				numberDiscardedOfCards++;
			}
		}
		
		CardAction action = new PlusCardAction(numberDiscardedOfCards);
		action.execute(state);
	}	
}
