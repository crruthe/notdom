package com.dominion.game.actions;

import java.util.LinkedList;
import java.util.List;

import com.dominion.game.GameState;
import com.dominion.game.cards.Card;
import com.dominion.game.interfaces.messages.CardTrashedMessage;


public class TrashCardAction implements CardAction {
	private boolean mustTrash = true;
	private int numCards = 0;
	
	// track cards that were trashed
	private LinkedList<Card> cards = new LinkedList<Card>();
	
	public TrashCardAction(int numCards, boolean mustTrash) {
		this.mustTrash = mustTrash;
		this.numCards = numCards;
	}
	
	public List<Card> getTrashedCards() {
		return cards;
	}
	
	@Override
	public void execute(GameState state) {

		Card card = null;
		
		for (int i = 0; i < numCards; i++) {
			
			// no need to trash if no cards available
			if (state.getCurrentPlayer().getHandSize() == 0) {
				break;
			}
			
			// if player must trash, then cycle until they select a card
			do {
				card = state.getCurrentPlayer().getCardToTrashFromHand();
			} while(mustTrash && card == null);
			
			// if they selected a card, then trash it
			if (card != null) {		
				cards.add(card);
				
				state.broadcastToAllPlayers(new CardTrashedMessage(state.getCurrentPlayer(), card));
		
				state.getCurrentPlayer().removeFromHand(card);
				state.getGameBoard().addToTrashPile(card);
			} else {
				// if they passed, no need to keep asking
				break;
			}
		}
	}
}
