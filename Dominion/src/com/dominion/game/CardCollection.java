package com.dominion.game;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.dominion.game.cards.Card;

/**
 * Generic container for anything that holds cards.
 * 
 */
public class CardCollection {
	private LinkedList<Card> cards = new LinkedList<Card>();
	
	/**
	 * Add a collection of cards to the deck
	 * @param cards
	 */
	public void addCards(Collection<Card> cards) {
		this.cards.addAll(cards);
	}	
	
	/**
	 * Add a card to the top of the deck (last card)
	 * @param card
	 */
	public void addCardToTop(Card card) {
		cards.add(card);		
	}
	
	/** 
	 * Look at the top card
	 * @return top card
	 */
	public Card getTopCard() {
		return cards.peekLast();
	}
	
	/**
	 * Clear the stack and return a copy
	 * @return
	 */
	public Collection<Card> clearCards() {
		List<Card> resultList = cards;
		
		cards = new LinkedList<Card>();
		
		return resultList;
	}
	
	/**
	 * @return number of cards in deck
	 */
	public int count() {
		return cards.size();
	}
	
	/**
	 * Draw a card from the top
	 * @return drawn card
	 */
	public Card removeTopCard() {
		if (cards.isEmpty()) { 
			return null;
		}
		
		return cards.removeLast();
	}

	public Collection<Card> getCards() {
		return cards;
	}
	
	/**
	 * @return whether is empty
	 */
	public boolean isEmpty() {
		return cards.isEmpty();
	}
	
	/**
	 * Shuffle the cards
	 */
	public void shuffle() {
		Collections.shuffle(cards);
	}
}
