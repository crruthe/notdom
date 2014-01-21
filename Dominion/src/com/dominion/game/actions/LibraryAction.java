package com.dominion.game.actions;

import java.util.LinkedList;

import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;

public class LibraryAction extends CardAction {
	@Override
	public void execute() {
		LinkedList<Card> setAside = new LinkedList<Card>();
		
		while (player.getCardHand().getSize() < 7) {
			Card card = player.drawCard();
			if (card instanceof ActionCard) {
				if (player.getPlayerInterface().wantsToSetAsideCard(card)) {
					setAside.add(card);
				} else {
					player.getCardHand().addCard(card);
				}				
			} else {
				player.getCardHand().addCard(card);
			}			
		}
		player.getDiscardPile().addCards(setAside);
	}	
}
