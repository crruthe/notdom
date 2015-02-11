package com.dominion.game.actions;

import com.dominion.game.GameState;
import com.dominion.game.cards.Card;

public class MoneylenderAction implements CardAction {
	@Override
	public void execute(GameState state) {
		Card card = state.getCurrentPlayer().getCopperCardToTrash();
		if (card != null) {
			state.getCurrentPlayer().removeFromHand(card);
			state.getGameBoard().addToTrashPile(card);
			state.getTurnState().incrementCoins(3);
		}		
	}	
}
