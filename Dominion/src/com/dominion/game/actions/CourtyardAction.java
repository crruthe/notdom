package com.dominion.game.actions;

import com.dominion.game.Player;
import com.dominion.game.interfaces.messages.SelectCardToPutOnDeckMessage;

public class CourtyardAction implements CardAction {
	@Override
	public void execute(Player player) {
		// No cards in the hand to return to the deck
		if (player.getHandSize() == 0) {
			return;
		}
		
		SelectCardToPutOnDeckMessage message = new SelectCardToPutOnDeckMessage(player.getCardHand());
		
		// The player must return a card
		do {
			player.invokeMessage(message);			
		} while (message.getCard() == null);
		
		player.moveCardFromHandToDeck(message.getCard());
	}	
}
