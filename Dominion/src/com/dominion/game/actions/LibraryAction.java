package com.dominion.game.actions;

import java.util.LinkedList;

import com.dominion.game.GameState;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;

public class LibraryAction implements CardAction {
	@Override
	public void execute(GameState state) {
		LinkedList<Card> setAside = new LinkedList<Card>();
		
		while (state.getCurrentPlayer().getHandSize() < 7) {
			Card card = state.getCurrentPlayer().drawCard();
			
			if (card == null) {
				break;
			}
			
			if (card instanceof ActionCard) {
				if (state.getCurrentPlayer().wantsToSetAsideCard(card)) {
					setAside.add(card);
				} else {
					state.getCurrentPlayer().addCardToHand(card);
				}				
			} else {
				state.getCurrentPlayer().addCardToHand(card);
			}			
		}
		
		state.getCurrentPlayer().addCardsToDiscardPile(setAside);
	}	
}
