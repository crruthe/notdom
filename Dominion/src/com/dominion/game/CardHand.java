package com.dominion.game;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.dominion.game.cards.Card;

public class CardHand {
	private LinkedList<Card> cards = new LinkedList<Card>();
	
	/**
	 * Add a card to a player's hand
	 * @param card
	 */
	public void addCard(Card card) {
		cards.add(card);
	}	
	
	public void addCards(Collection<Card> cards) {
		this.cards.addAll(cards);
	}
	
	/**
	 * Empties the hand and returns the cards
	 * @return collection containing the discarded hand
	 */
	public Collection<Card> clearHand() {
		List<Card> resultList = cards;
		
		cards = new LinkedList<Card>();
		
		return resultList;		
	}

	/**
	 * @return reference to player's hand
	 */
	public List<Card> getCards() {
		return cards;
	}
	
	public int getSize() {
		return cards.size();
	}


	public void removeCard(Card card) {
		cards.remove(card);
	}
}
