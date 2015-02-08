package com.dominion.game.actions;

import com.dominion.game.cards.Card;

public class CourtyardAction extends CardAction {
	@Override
	public void execute() {
		Card card = player.getCardToDiscard();
		player.moveCardFromHandToDeck(card);
	}	
}
