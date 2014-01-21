package com.dominion.game.actions;

import java.util.LinkedList;

import com.dominion.game.cards.Card;
import com.dominion.game.cards.TreasureCard;

public class AdventurerAction extends CardAction {
	@Override
	public void execute() {
		LinkedList<Card> setAside = new LinkedList<Card>();
		int numberOfTreasureCards = 0;
		
		while (numberOfTreasureCards < 2) {
			Card card = player.drawCard();
			
			if (card == null) {
				numberOfTreasureCards = 2;
			} else if (card instanceof TreasureCard) {
				player.getCardHand().addCard(card);
				numberOfTreasureCards++;
			} else {
				setAside.add(card);
			}
		}
		player.getDiscardPile().addCards(setAside);
	}	
}
