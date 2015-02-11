package com.dominion.game.actions;

import java.util.List;

import com.dominion.game.GameState;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.TreasureCard;

/**
 * 
 * @author Vomit
 *
 * Trash a Treasure card from your hand. Gain a Treasure card costing up to 3 Coins more; put it into your hand.
 */
public class MineAction implements CardAction {
	@Override
	public void execute(GameState state) {
		Card trashCard = (Card)state.getCurrentPlayer().getTreasureCardToTrash();
		if (trashCard == null) {
			return;
		}
				
		state.getCurrentPlayer().removeFromHand(trashCard);
		state.getGameBoard().addToTrashPile(trashCard);
		
		// Find a treasure card to replace the trashed card
		List<Card> cards = state.getGameBoard().listCardsFilterByClassAndCost(TreasureCard.class, trashCard.getCost() + 3);
		Card card = state.getCurrentPlayer().getTreasureCardToGain(cards);
		
		state.getCurrentPlayer().addCardToHand(card);
	}
}
