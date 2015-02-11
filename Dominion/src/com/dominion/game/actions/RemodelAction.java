package com.dominion.game.actions;

import com.dominion.game.Player;
import com.dominion.game.cards.Card;
import com.dominion.game.interfaces.messages.SelectCardToTrashFromHandMessage;

/**
 * 
 * @author Vomit
 *
 * Trash a card from your hand. Gain a card costing up to 2 Coins more than the trashed card.
 */
public class RemodelAction implements CardAction {
	@Override
	public void execute(Player player) {
		// No cards to trash
		if (player.getHandSize() == 0) {
			return;
		}
				
		SelectCardToTrashFromHandMessage message = new SelectCardToTrashFromHandMessage(player.getCardHand());			
		player.invokeMessage(message);
		
		// Player must select a card to trash, if possible
		while (message.getCard() == null) {
			player.invokeMessage(message);
		}
		
		Card trashCard = message.getCard();
		
		player.trashCardFromHand(trashCard);
		
		Card card = player.getCardToGain(trashCard.getCost() + 2);
		
		if (card != null) {
			player.gainCardFromSupply(card.getName());
		}
	}
}
