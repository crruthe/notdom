package com.dominion.game.actions;

import com.dominion.game.Player;
import com.dominion.game.cards.Card;

public class CellarAction implements CardAction {
	@Override
	public void execute(Player player) {
		boolean endAction = false;
		int numberDiscardedOfCards = 0;
		
		while (!endAction) {
			Card card = player.getCardToDiscard();
			
			if (card == null) {
				endAction = true;
			} else {
				player.discardCardFromHand(card);
				numberDiscardedOfCards++;
			}
		}
		
		CardAction action = new PlusCardAction(numberDiscardedOfCards);
		action.execute(player);
	}	
}
