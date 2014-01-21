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
		Card trashCard = player.getPlayerInterface().getTreasureCardToTrash();
		
		if (!(trashCard instanceof TreasureCard)) {
			throw new RuntimeException("trash card not a treasure card");
		}
		
		player.trashCard(trashCard);
		
		Card card = player.getPlayerInterface().getCardToGain(trashCard.getCost() + 3);
		player.getCardHand().addCard(card);
	}
}
