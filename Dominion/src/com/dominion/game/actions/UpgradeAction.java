package com.dominion.game.actions;

import java.util.List;

import com.dominion.game.GameState;
import com.dominion.game.cards.Card;
import com.dominion.game.interfaces.messages.CardTrashedMessage;

/**
 * 
 * @author Vomit
 *
 * Trash a card from your hand. Gain a card costing exactly 1 more coins than the trashed card.
 */
public class UpgradeAction implements CardAction {
	@Override
	public void execute(GameState state) {
		// No cards to trash
		if (state.getCurrentPlayer().getHandSize() == 0) {
			return;
		}
				
		// Player must select a card to trash, if possible
		Card trashCard = null;
		while (trashCard == null) {
			trashCard = state.getCurrentPlayer().getCardToTrashFromHand();
		}
		
		state.broadcastToAllPlayers(new CardTrashedMessage(state.getCurrentPlayer(), trashCard));
		
		state.getGameBoard().addToTrashPile(trashCard);
		state.getCurrentPlayer().removeFromHand(trashCard);
		
		// Get actual cost after modifiers e.g. Bridge
		int cost = state.modifyCard(trashCard).getCost();

		// Gain a card that is exactly cost + 1
		List<Card> cards = state.listCardsFilterByCost(cost + 1, cost + 1);
		
		// If there is no card of this amount, no need to gain
		if (cards.isEmpty()) {
			return;
		}
		
		// Player must gain a card
		Card gainCard = null;
		while (gainCard == null) {
			gainCard = state.getCurrentPlayer().getCardToBuy(cards);
		}
		state.getCurrentPlayer().addCardToDiscardPile(gainCard);
		state.getGameBoard().removeCardFromSupply(gainCard.getClass());
	}
}
