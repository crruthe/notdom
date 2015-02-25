package com.dominion.game.actions;

import java.util.LinkedList;
import java.util.List;

import com.dominion.game.GameState;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.VictoryCard;
import com.dominion.game.interfaces.messages.CardRevealedMessage;

public class ScoutAction implements CardAction {
	@Override
	public void execute(GameState state) {
		List<Card> cards = new LinkedList<Card>();
		
		// Reveal 4 cards
		for (int i = 0; i < 4; i++) {
			// Reveal the top card
			Card card = state.getCurrentPlayer().drawCard();
			
			// User has no more cards
			if (card == null) {
				break;
			}
			
			// Add revealed cards to hand
			if (card instanceof VictoryCard) {
				state.getCurrentPlayer().addCardToHand(card);
			} else {
				// Keep track of the cards drawn
				cards.add(card);
			}
			
			state.broadcastToAllPlayers(new CardRevealedMessage(state.getCurrentPlayer(), card));
		}

		// Return all cards back to deck in a specific order
		while (!cards.isEmpty()) {
			Card returnCard = null;
			
			while (returnCard == null) {
				returnCard = state.getCurrentPlayer().getCardToPutOnDeckScout(cards);
			}
			
			cards.remove(returnCard);
			state.getCurrentPlayer().addCardToCardDeck(returnCard);
		}		
	}
}
