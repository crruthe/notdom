package com.dominion.game.actions;

import java.util.LinkedList;

import com.dominion.game.GameBoard;
import com.dominion.game.Player;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.TreasureCard;
import com.dominion.game.interfaces.messages.CardRevealedMessage;

public class ThiefAction extends AttackAction {
	@Override
	public void execute() {
		super.execute();
	}

	@Override
	public void executeAttackOnPlayer(Player attackPlayer) {
		LinkedList<Card> discard = new LinkedList<Card>();
		
		// Reveal the top two cards of their deck
		Card first = attackPlayer.drawCard();
		Card second = attackPlayer.drawCard();		
		player.broadcastMessage(new CardRevealedMessage(attackPlayer, first));
		player.broadcastMessage(new CardRevealedMessage(attackPlayer, second));
		

		// If the any of the cards are treasure, you must trash one, discard the others
		Card trash = null;
		if (first instanceof TreasureCard && second instanceof TreasureCard) {
			if (player.wantsToTrashCard(first)) {
				attackPlayer.trashCard(first);
				trash = first;
				discard.add(second);
			} else {
				attackPlayer.trashCard(second);
				trash = second;
				discard.add(first);
			}
		} else if (first instanceof TreasureCard) {
			attackPlayer.trashCard(first);
			trash = first;
			discard.add(second);
		} else if (second instanceof TreasureCard) {
			attackPlayer.trashCard(second);
			trash = second;
			discard.add(first);
		} else {
			discard.add(first);
			discard.add(second);
		}
		
		// You may gain this trashed card
		if (trash != null) {
			if (player.wantsToGainCard(trash)) {
				player.addCardToDiscardPile(trash);
			}
		}
		
		// Discard the rest of the cards
		for (Card card: discard) {
			attackPlayer.addCardToDiscardPile(card);
		}
	}	
}
