package com.dominion.game.actions;

import com.dominion.game.cards.Card;
import com.dominion.game.cards.TreasureCard;

/**
 * 
 * @author Vomit
 *
 * Trash a Treasure card from your hand. Gain a Treasure card costing up to 3 Coins more; put it into your hand.
 */
public class MineAction extends CardAction {
	@Override
	public void execute() {
		Card trashCard = player.getTreasureCardToTrash();
		if (trashCard == null) {
			return;
		}
		
		if (!(trashCard instanceof TreasureCard)) {
			throw new RuntimeException("trash card not a treasure card");
		}
		
		player.trashCardFromHand(trashCard);
		
		Card card = player.getTreasureCardToGain(trashCard.getCost() + 3);
		player.addCardToHand(card);
	}
}
