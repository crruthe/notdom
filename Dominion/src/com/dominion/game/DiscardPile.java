package com.dominion.game;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.dominion.game.cards.Card;


public class DiscardPile {
	private final LinkedList<Card> cards = new LinkedList<Card>();
	
	/**
	 * @return collection containing the discard pile
	 * Empties the discard pile and returns the cards
	 */
	public Collection<Card> clearDiscardPile() {		
		List<Card> resultList = new LinkedList<Card>(cards);
		Collections.copy(resultList, cards);
		
		cards.clear();
		
		return resultList;
	}	

	/**
	 * @return number of cards in discard pile
	 */
	public int count() {
		return cards.size();
	}
	
	/**
	 * @return whether the discard pile is empty
	 */
	public boolean isEmpty() {
		return cards.isEmpty();
	}
	
	/**
	 * @param cards
	 * Add a collection of cards to the discard pile
	 */
	public void addCards(Collection<Card> cards) {
		this.cards.addAll(cards);
	}
	
	/**
	 * @param card
	 * Add a card to the discard pile
	 */
	public void addCard(Card card) {
		cards.add(card);
	}
	
	public Card getTopCard() {
		return cards.get(cards.size());
	}
}
