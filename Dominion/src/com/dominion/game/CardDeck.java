package com.dominion.game;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.dominion.game.cards.Card;


/**
 * @author Vomit
 *
 */
public class CardDeck {
	private final LinkedList<Card> cards = new LinkedList<Card>();
		
	/**
	 * Shuffle the deck
	 */
	public void shuffle() {
		Collections.shuffle(cards);
	}	
	
	/**
	 * @param cards
	 * Add a collection of cards to the deck
	 */
	public void addCards(Collection<Card> cards) {
		this.cards.addAll(cards);
	}
	
	/**
	 * @param card
	 * Add a card to the deck
	 */
	public void addCard(Card card) {
		cards.add(card);
	}
	
	/**
	 * @return drawn card
	 * Draw a card from the deck
	 */
	public Card drawCard() {
		if (cards.isEmpty()) { 
			return null;
		}
		
		return cards.removeFirst();
	}
	
	/**
	 * @return number of cards in deck
	 */
	public int count() {
		return cards.size();
	}

	/**
	 * @return whether deck is empty
	 */
	public boolean isEmpty() {
		return cards.isEmpty();
	}
	
	public Collection<Card> getCards() {
		return cards;
	}
	
	public Collection<Card> clearCardDeck() {
		List<Card> resultList = new LinkedList<Card>(cards);
		Collections.copy(resultList, cards);
		
		cards.clear();
		
		return resultList;
	}
}
