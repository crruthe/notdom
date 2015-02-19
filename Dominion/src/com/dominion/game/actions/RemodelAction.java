package com.dominion.game.actions;

import java.util.List;

import com.dominion.game.GameState;
import com.dominion.game.cards.Card;

/**
 * 
 * @author Vomit
 *
 * Trash a card from your hand. Gain a card costing up to 2 Coins more than the trashed card.
 */
public class RemodelAction implements CardAction {
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
		
		state.getGameBoard().addToTrashPile(trashCard);
		state.getCurrentPlayer().removeFromHand(trashCard);

		// Get actual cost after modifiers e.g. Bridge
		int cost = trashCard.modifyCard(state.getTurnState().getModifiers()).getCost();
		
		List<Card> cards = state.listCardsFilterByCost(cost + 2);
		
		// Player must gain a card
		Card gainCard = null;
		while (gainCard == null) {
			gainCard = state.getCurrentPlayer().getCardToBuy(cards);
		}
		state.getCurrentPlayer().addCardToDiscardPile(gainCard);
		state.getGameBoard().removeCardFromSupply(gainCard.getClass());
	}
}
