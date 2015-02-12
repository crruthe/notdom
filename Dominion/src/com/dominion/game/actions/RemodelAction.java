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
		
		List<Card> cards = state.getGameBoard().listCardsFilterByCost(trashCard.getCost() + 2);
		
		Card card = state.getCurrentPlayer().getCardToBuy(cards);
		
		if (card != null) {
			state.getCurrentPlayer().addCardToDiscardPile(card);
			state.getGameBoard().removeCardFromSupply(card.getClass());
		}
	}
}
