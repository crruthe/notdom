package com.dominion.game.actions;

import com.dominion.game.Player;
import com.dominion.game.interfaces.messages.SelectCardToTrashFromHandMessage;

public class ChapelAction implements CardAction {
	@Override
	public void execute(Player player) {
		int numberToTrash = 4;
		
		while (numberToTrash > 0) {
			// No cards to trash
			if (player.getHandSize() == 0) {
				return;
			}
			
			SelectCardToTrashFromHandMessage message = new SelectCardToTrashFromHandMessage(player.getCardHand());
			player.invokeMessage(message);
			
			if (message.getCard() == null) {
				break;
			} else {
				player.trashCardFromHand(message.getCard());
				numberToTrash--;
			}
		}
	}	
}
