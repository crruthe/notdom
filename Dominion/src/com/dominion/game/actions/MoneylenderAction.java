package com.dominion.game.actions;

import com.dominion.game.GameState;
import com.dominion.game.cards.Card;
import com.dominion.game.interfaces.messages.CardTrashedMessage;

public class MoneylenderAction implements CardAction {
	@Override
	public void execute(GameState state) {
		Card card = state.getCurrentPlayer().getCopperCardToTrash();
		if (card != null) {

			state.broadcastToAllPlayers(new CardTrashedMessage(state.getCurrentPlayer(), card));
			
			state.getCurrentPlayer().removeFromHand(card);
			state.getGameBoard().addToTrashPile(card);
			
			CardAction action = new PlusCoinAction(3);
			action.execute(state);
		}		
	}	
}
