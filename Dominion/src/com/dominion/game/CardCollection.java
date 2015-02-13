package com.dominion.game;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

import com.dominion.game.cards.Card;

/**
 * Generic container for anything that holds cards.
 * 
 */
public class CardCollection extends Observable {
	
	private LinkedList<Card> cards = new LinkedList<Card>();
	
	/**
	 * Add a collection of cards to the deck
	 * @param cards
	 */
	public void addCards(Collection<Card> newCards) {
		cards.addAll(newCards);
		setChanged();
		notifyObservers();		
	}	
	
	/**
	 * Add a card to the top of the deck (last card)
	 * @param card
	 */
	public void addCardToTop(Card card) {
		if (card == null) {
			throw new NullPointerException();
		}
		cards.add(card);
		setChanged();
		notifyObservers();
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
	public List<Card> clearCards() {
		List<Card> resultList = cards;
		
		cards = new LinkedList<Card>();
		setChanged();
		notifyObservers();
		
		return resultList;
	}
	
	/**
	 * @return number of cards in deck
	 */
	public int count() {
		return cards.size();
	}
	
	/**
	 * Remove the card from the top and return it
	 * @return drawn card
	 */
	public Card removeTopCard() {
		if (cards.isEmpty()) { 
			return null;
		}
		setChanged();
		notifyObservers();
		return cards.removeLast();
	}

	public List<Card> getCards() {
		return cards;
	}

	/**
	 * Generic method to filter and return cards by type e.g. VictoryCards
	 * @param cardClass
	 * @return
	 */
	public List<Card> getCardsFilterByClass(Class<?> cardClass) {
		
		// Build a list of cards from the collection filtered by class
		LinkedList<Card> resultCards = new LinkedList<Card>();
		for (Card card : cards) {
			if (cardClass.isInstance(card)) {
				resultCards.add((Card)card);
			}
		}
		Collections.sort(resultCards);
		return resultCards;
	}
	
	/**
	 * Removes the card from the collection
	 * @param card
	 */
	public void removeCard(Card card) {
		cards.remove(card);
		setChanged();
		notifyObservers();
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
		setChanged();
		notifyObservers();
	}
}
