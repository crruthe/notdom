package com.dominion.game.actions;

import com.dominion.game.GameState;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.basic.SilverCard;

public class TradingPostAction implements CardAction {
	@Override
	public void execute(GameState state) {
		TrashCardAction trashCardAction = new TrashCardAction(2, true);
		trashCardAction.execute(state);
		
		if (trashCardAction.getTrashedCards().size() == 2) {
			Card card = state.getGameBoard().removeCardFromSupply(SilverCard.class);
			
			// card is null if none left
			if (card != null) {
				state.getCurrentPlayer().addCardToHand(card);			
			}
		}		
	}	
}
