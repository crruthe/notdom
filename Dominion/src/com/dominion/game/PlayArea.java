package com.dominion.game;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.dominion.game.cards.Card;

public class PlayArea {
	private final LinkedList<Card> cards = new LinkedList<Card>();

	public Collection<Card> clearPlayArea() {
		List<Card> resultList = new LinkedList<Card>(cards);
		Collections.copy(resultList, cards);
		
		cards.clear();
		
		return resultList;		
	}

	public void addCard(Card card) {
		cards.add(card);
	}
}
