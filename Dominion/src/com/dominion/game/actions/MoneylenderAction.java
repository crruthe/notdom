package com.dominion.game.actions;

import com.dominion.game.Player;
import com.dominion.game.cards.Card;

public class MoneylenderAction implements CardAction {
	@Override
	public void execute(Player player) {
		Card card = player.getCopperCardToTrash();
		if (card != null) {
			player.trashCardFromHand(card);
			player.incrementCoins(3);
		}		
	}	
}
