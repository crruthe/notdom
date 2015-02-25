package com.dominion.game.actions;

import com.dominion.game.GameState;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.basic.SilverCard;
import com.dominion.game.interfaces.messages.CardTrashedMessage;

public class TradingPostAction implements CardAction {
	@Override
	public void execute(GameState state) {
		
		// Trash two cards
		for (int i = 0; i < 2; i++) {
			
			// No cards to trash
			if (state.getCurrentPlayer().getHandSize() == 0) {
				return;
			}
			
			// Get a card to trash
			Card trashCard = null;
			while (trashCard == null) {
				trashCard = state.getCurrentPlayer().getCardToTrashFromHand();				
			}
			state.getCurrentPlayer().removeFromHand(trashCard);
			state.getGameBoard().addToTrashPile(trashCard);
			
			state.broadcastToAllPlayers(new CardTrashedMessage(state.getCurrentPlayer(), trashCard));
		}
		
		Card card = state.getGameBoard().removeCardFromSupply(SilverCard.class);
		
		// No cards left in stack
		if (card == null) {
			return;
		}
		
		state.getCurrentPlayer().addCardToHand(card);
	}	
}
