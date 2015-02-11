package com.dominion.game;

import com.dominion.game.cards.Card;
import com.dominion.game.cards.basic.*;

import junit.framework.TestCase;

public class CardDeckTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testAddCardToTopAndDrawCard() {
		CardDeck cardDeck = new CardDeck();
		cardDeck.addCardToTop(new GoldCard());
		cardDeck.addCardToTop(new SilverCard());
		cardDeck.addCardToTop(new CopperCard());
		cardDeck.addCardToTop(new EstateCard());
		
		Card card1 = cardDeck.drawCard();
		assertEquals(card1.getClass(), EstateCard.class);
		
		Card card2 = cardDeck.drawCard();
		assertEquals(card2.getClass(), CopperCard.class);

		Card card3 = cardDeck.drawCard();
		assertEquals(card3.getClass(), SilverCard.class);

		Card card4 = cardDeck.drawCard();
		assertEquals(card4.getClass(), GoldCard.class);
	}
	
	public void testDrawCardOnEmptyDeckReturnsNull() {
		CardDeck cardDeck = new CardDeck();
		Card card = cardDeck.drawCard();
		
		assertNull(card);
	}
}
