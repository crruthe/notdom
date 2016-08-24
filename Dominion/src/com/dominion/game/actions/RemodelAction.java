package com.dominion.game.actions;

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
		TrashCardAction trashCardAction = new TrashCardAction(1, true);
		trashCardAction.execute(state);
		
		if (trashCardAction.getTrashedCards().size() == 1) { 
			Card trashedCard = trashCardAction.getTrashedCards().get(0);

			// Get actual cost after modifiers e.g. Bridge
			int cost = state.getModifiedCost(trashedCard);
			
			new GainCardAction(cost + 2, true).execute(state);
		}
	}
}
