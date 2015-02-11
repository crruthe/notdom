package com.dominion.game.actions;

import java.util.LinkedList;

import com.dominion.game.GameState;
import com.dominion.game.Player;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.TreasureCard;
import com.dominion.game.interfaces.messages.CardRevealedMessage;

public class ThiefAction extends AttackAction {
	@Override
	public void executeAttackOnPlayer(GameState state, Player victim) {
		LinkedList<Card> discard = new LinkedList<Card>();
		
		// Reveal the top two cards of their deck
		Card first = victim.drawCard();
		Card second = victim.drawCard();		
		state.broadcastToAllPlayers(new CardRevealedMessage(victim, first));
		state.broadcastToAllPlayers(new CardRevealedMessage(victim, second));
		

		// If the any of the cards are treasure, you must trash one, discard the others
		
		Card trash = null;
		if (first instanceof TreasureCard && second instanceof TreasureCard) {
			if (state.getCurrentPlayer().wantsToTrashCard(first)) {
				victim.removeFromHand(first);
				trash = first;
				discard.add(second);
			} else {
				victim.removeFromHand(second);
				trash = second;
				discard.add(first);
			}
		} else if (first instanceof TreasureCard) {
			victim.removeFromHand(first);
			trash = first;
			discard.add(second);
		} else if (second instanceof TreasureCard) {
			victim.removeFromHand(second);
			trash = second;
			discard.add(first);
		} else {
			discard.add(first);
			discard.add(second);
		}
		
		// You may gain this trashed card
		if (trash != null) {
			if (state.getCurrentPlayer().wantsToGainCard(trash)) {
				state.getCurrentPlayer().addCardToDiscardPile(trash);
			} else {
				state.getGameBoard().addToTrashPile(trash);
			}
		}
		
		// Discard the rest of the cards
		for (Card card: discard) {
			victim.addCardToDiscardPile(card);
		}
	}	
}
