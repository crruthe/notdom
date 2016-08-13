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
			// Get actual cost after modifiers e.g. Bridge
			Card trashedCard = trashCardAction.getTrashedCards().get(0);
			int cost = state.modifyCard(trashedCard).getCost();
			new GainCardAction(cost + 2, true).execute(state);
		}
	}
}
