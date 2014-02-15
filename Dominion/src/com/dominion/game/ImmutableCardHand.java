package com.dominion.game;

import java.util.LinkedList;
import java.util.List;

import com.dominion.game.cards.Card;

public class ImmutableCardHand {
	private final LinkedList<Card> cards;
	
	public ImmutableCardHand(CardHand cardHand) {
		this.cards = new LinkedList<Card>(cardHand.getCards());
	}
	
	public List<Card> getCards() {
		return cards;
	}
}
