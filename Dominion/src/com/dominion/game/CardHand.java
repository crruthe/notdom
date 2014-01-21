package com.dominion.game;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.dominion.game.cards.Card;

public class CardHand {
	private final LinkedList<Card> cards = new LinkedList<Card>();
	
	/**
	 * @param card
	 * Add a card to a player's hand
	 */
	public void addCard(Card card) {
		cards.add(card);
	}
	
	
	/**
	 * @return reference to player's hand
	 */
	public List<Card> getCards() {
		return cards;
	}
	
	public void addCards(Collection<Card> cards) {
		this.cards.addAll(cards);
	}

	/**
	 * @return collection containing the discarded hand
	 * Empties the hand and returns the cards
	 */
	public Collection<Card> clearHand() {
		List<Card> resultList = new LinkedList<Card>(cards);
		Collections.copy(resultList, cards);
		
		cards.clear();
		
		return resultList;		
	}
	
	public void removeCard(Card card) {
		cards.remove(card);
	}


	public int getSize() {
		return cards.size();
	}
}
