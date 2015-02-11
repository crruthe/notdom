package com.dominion.game;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.dominion.game.cards.Card;

public class PlayArea {
	private LinkedList<Card> cards = new LinkedList<Card>();

	public Collection<Card> clearPlayArea() {
		List<Card> resultList = cards;
		
		cards = new LinkedList<Card>();
		
		return resultList;		
	}

	public void addCard(Card card) {
		cards.add(card);
	}
	
	public List<Card> getCards() {
		return cards;
	}
	
	public void removeCard(Card card) {
		cards.remove(card);		
	}
}
