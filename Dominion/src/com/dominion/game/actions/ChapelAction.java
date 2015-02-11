package com.dominion.game.actions;

import com.dominion.game.GameState;
import com.dominion.game.cards.Card;

public class ChapelAction implements CardAction {
	@Override
	public void execute(GameState state) {
		int numberToTrash = 4;
		
		while (numberToTrash > 0) {
			// No cards to trash
			if (state.getCurrentPlayer().getHandSize() == 0) {
				return;
			}
			
			Card card = state.getCurrentPlayer().getCardToTrashFromHand();
			
			if (card == null) {
				break;
			} else {
				state.getCurrentPlayer().removeFromHand(card);
				state.getGameBoard().addToTrashPile(card);
				numberToTrash--;
			}
		}
	}	
}
