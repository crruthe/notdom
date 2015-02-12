package com.dominion.game.actions;

import java.util.LinkedList;

import com.dominion.game.GameState;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.TreasureCard;

public class AdventurerAction implements CardAction {
	@Override
	public void execute(GameState state) {
		LinkedList<Card> setAside = new LinkedList<Card>();
		int numberOfTreasureCards = 0;
		
		while (numberOfTreasureCards < 2) {
			Card card = state.getCurrentPlayer().drawCard();
			
			// No cards left to draw
			if (card == null) {	
				break;
			}
			
			if (card instanceof TreasureCard) {
				state.getCurrentPlayer().addCardToHand(card);
				numberOfTreasureCards++;
			} else {
				setAside.add(card);
			}
		}
		
		state.getCurrentPlayer().addCardsToDiscardPile(setAside);
	}	
}
