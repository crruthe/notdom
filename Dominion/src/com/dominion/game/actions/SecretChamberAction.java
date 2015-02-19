package com.dominion.game.actions;

import com.dominion.game.GameState;
import com.dominion.game.cards.Card;

/**
 * Discard any number of cards. +1 coin per card discarded.
 * 
 * @author user
 *
 */
public class SecretChamberAction implements CardAction {
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
		
		CardAction action = new PlusCoinAction(numberDiscardedOfCards);
		action.execute(state);
	}	
}
