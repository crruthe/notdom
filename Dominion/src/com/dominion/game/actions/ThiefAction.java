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
		LinkedList<Card> cards = new LinkedList<Card>();
		
		// Reveal the top two cards of their deck
		for (int i = 0; i < 2; i++) {
			Card card = victim.drawCard();
			
			// User doesn't have anymore cards
			if (card == null) {
				break;
			}
			
			state.broadcastToAllPlayers(new CardRevealedMessage(victim, card));
			
			if (card instanceof TreasureCard) {
				cards.add(card);
			} else {
				victim.addCardToDiscardPile(card);
			}
		}
		
		// If no treasure cards were drawn, nothing else to do
		if (cards.isEmpty()) {
			return;
		}
		
		Card trashCard = null;
		while (trashCard == null) {
			trashCard = state.getCurrentPlayer().getCardToTrashThief(cards);
		}
		cards.remove(trashCard);
		
		// Add the remaining to the players discard
		victim.addCardsToDiscardPile(cards);
		
		// Allow the player to keep the card
		if (state.getCurrentPlayer().wantsToGainCardThief(trashCard)) {
			state.getCurrentPlayer().addCardToDiscardPile(trashCard);
		} else {
			state.getGameBoard().addToTrashPile(trashCard);
		}		
	}	
}
