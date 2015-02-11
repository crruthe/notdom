package com.dominion.game;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.dominion.game.cards.Card;


public class DiscardPile {
	private LinkedList<Card> cards = new LinkedList<Card>();
	
	/**
	 * Empties the discard pile and returns the cards
	 * @return collection containing the discard pile
	 */
	public Collection<Card> clearDiscardPile() {		
		List<Card> resultList = cards;
		
		cards = new LinkedList<Card>();
		
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
	 * Add a collection of cards to the discard pile
	 * @param cards
	 */
	public void addCards(Collection<Card> cards) {
		this.cards.addAll(cards);
	}
	
	/**
	 * Add a card to the top of discard pile (last card)
	 * @param card
	 */
	public void addCard(Card card) {
		cards.add(card);
	}
	
	/**
	 * Since cards are added to end, return the last card 
	 * @return top card on the discard pile
	 */
	public Card getTopCard() {
		if (cards.isEmpty()) {
			return null;
		}
		
		return cards.getLast();
	}
}
