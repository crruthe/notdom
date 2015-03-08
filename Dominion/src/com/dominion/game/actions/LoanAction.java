package com.dominion.game.actions;

import java.util.HashMap;
import java.util.LinkedList;

import com.dominion.game.GameState;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.TreasureCard;
import com.dominion.game.interfaces.messages.ActionSelectedMessage;
import com.dominion.game.interfaces.messages.CardRevealedMessage;
import com.dominion.game.interfaces.messages.CardTrashedMessage;

public class LoanAction implements CardAction {
	
	class DiscardCardLoanCardAction implements CardAction {
		final Card card;
		
		DiscardCardLoanCardAction(Card card) {
			this.card = card;
		}
		
		@Override
		public void execute(GameState state) {
			state.getCurrentPlayer().addCardToDiscardPile(card);	
		}		
	}
	
	class TrashCardLoadCardAction implements CardAction {		
		final Card card;
		
		TrashCardLoadCardAction(Card card) {
			this.card = card;
		}
		
		@Override
		public void execute(GameState state) {
			state.broadcastToAllPlayers(new CardTrashedMessage(state.getCurrentPlayer(), card));
			
			state.getGameBoard().addToTrashPile(card);
		}		
	}
	
	@Override
	public void execute(GameState state) {
		LinkedList<Card> setAside = new LinkedList<Card>();		

		HashMap<String, CardAction> actions = new HashMap<String, CardAction>();
		
		// Keep drawing cards until they run out of cards or draw a treasure card
		while (true) {
			Card card = state.getCurrentPlayer().drawCard();
			
			// User has no more cards
			if (card == null) {
				break;
			}		
			
			state.broadcastToAllPlayers(new CardRevealedMessage(state.getCurrentPlayer(), card));
			
			if (card instanceof TreasureCard) {
				// Give them the option to discard this card or trash it
				actions.put("Discard Card", new DiscardCardLoanCardAction(card));
				actions.put("Trash Card", new TrashCardLoadCardAction(card));

				String action = null;
				while (!actions.containsKey(action)) {
					action = state.getCurrentPlayer().getCardActionToPlay(actions);
				}

		    	state.broadcastToAllPlayers(new ActionSelectedMessage(state.getCurrentPlayer(), action));
				actions.get(action).execute(state);
				
				break;
			} else {
				// Track the cards that were revealed and not treasure cards
				setAside.add(card);
			}			
		}
		
		// Discard the rest of the drawn cards
		state.getCurrentPlayer().addCardsToDiscardPile(setAside);
	}	
}
