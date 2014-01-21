package com.dominion.game.actions;

import com.dominion.game.cards.Card;

public class CellarAction extends CardAction {
	@Override
	public void execute() {
		boolean endAction = false;
		int numberDiscardedOfCards = 0;
		
		while (!endAction) {
			Card card = player.getPlayerInterface().getCardToDiscard();
			
			if (card == null) {
				endAction = true;
			} else {
				player.discardCard(card);
				numberDiscardedOfCards++;
			}
		}
		
		CardAction action = new PlusCardAction(numberDiscardedOfCards);
		action.setPlayer(player);
		action.execute();
	}	
}
