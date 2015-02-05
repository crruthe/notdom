package com.dominion.game.actions;

import com.dominion.game.cards.Card;

public class ChapelAction extends CardAction {
	@Override
	public void execute() {
		int numberToTrash = 4;
		
		while (numberToTrash > 0) {
			Card card = player.getCardToTrash();
			
			if (card == null) {
				break;
			} else {
				player.trashCardFromHand(card);
				numberToTrash--;
			}
		}
	}	
}
