package com.dominion.game.actions;

import com.dominion.game.cards.Card;

public class ChapelAction extends CardAction {
	@Override
	public void execute() {
		boolean endAction = false;
		int numberToTrash = 4;
		
		while (!endAction && numberToTrash > 0) {
			Card card = player.getCardToTrash();
			
			if (card == null) {
				endAction = true;
			} else {
				player.trashCard(card);
				numberToTrash--;
			}
		}
	}	
}
