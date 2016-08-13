package com.dominion.game.actions;

import java.util.List;

import com.dominion.game.GameState;
import com.dominion.game.cards.Card;
import com.dominion.game.interfaces.messages.CardGainedMessage;

/**
 * 
 * @author Vomit
 *
 * Trash a card from your hand. Gain a card costing exactly 1 more coins than the trashed card.
 */
public class UpgradeAction implements CardAction {
	@Override
	public void execute(GameState state) {
		TrashCardAction trashCardAction = new TrashCardAction(1, true);
		trashCardAction.execute(state);
		
		if (trashCardAction.getTrashedCards().size() == 1) {
			Card trashCard = trashCardAction.getTrashedCards().get(0);
			
			// Get actual cost after modifiers e.g. Bridge
			int cost = state.modifyCard(trashCard).getCost();

			// Gain a card that is exactly cost + 1
			List<Card> cards = state.listCardsFilterByCostExact(cost + 1);
			
			// If there is no card of this amount, no need to gain
			if (cards.isEmpty()) {
				return;
			}
			
			// Player must gain a card
			Card gainCard = null;
			while (gainCard == null) {
				gainCard = state.getCurrentPlayer().getCardToGainExact(cards, cost+1);
			}
			state.getCurrentPlayer().addCardToDiscardPile(gainCard);
			state.getGameBoard().removeCardFromSupply(gainCard.getClass());
			state.broadcastToAllPlayers(new CardGainedMessage(state.getCurrentPlayer(), gainCard));					
		}		
	}
}
