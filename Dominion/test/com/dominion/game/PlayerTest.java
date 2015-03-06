package com.dominion.game;

import junit.framework.TestCase;

import com.dominion.game.cards.Card;
import com.dominion.game.cards.basic.CopperCard;
import com.dominion.game.cards.basic.GoldCard;
import com.dominion.game.cards.basic.SilverCard;

public class PlayerTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testAddCardToCardDeck() {
		Player player = new Player(new MockPlayerInterface());
		player.addCardToCardDeck(new CopperCard());
		player.addCardToCardDeck(new SilverCard());
		player.addCardToCardDeck(new GoldCard());

		Card card = player.drawCard();		
		assertEquals(GoldCard.class, card.getClass());
	}
/*
	public void testDrawCardWithTwoCardsInDeck() {
		Player player = new Player(new MockPlayerInterface());
		player.addCardToCardDeck(new CopperCard());
		player.addCardToCardDeck(new SilverCard());

		Card card = player.drawCard();		
		assertEquals("Draw card returns wrong card (first draw)", CopperCard.class, card.getClass());
		
		Card card2 = player.drawCard();		
		assertEquals("Draw card returns wrong card (second draw)", SilverCard.class, card2.getClass());
	}
	
	public void testDrawCardWithOneCardInDiscardPile() {
		Player player = new Player(new MockPlayerInterface());
		player.gainCard(new CopperCard());
		
		Card card = player.drawCard();		
		assertEquals("Draw card returns wrong card", CopperCard.class, card.getClass());
	}
	
	public void testDrawCardWithOneCardInCardDeckAndOneCardInDiscardPile() {
		Player player = new Player(new MockPlayerInterface());
		player.addCardToCardDeck(new CopperCard());
		player.gainCard(new SilverCard());
		
		Card card = player.drawCard();		
		assertEquals("Draw card returns wrong card (first draw)", CopperCard.class, card.getClass());

		Card card2 = player.drawCard();		
		assertEquals("Draw card returns wrong card (second draw)", SilverCard.class, card2.getClass());
	}
	
	public void testDrawCardToHandWithOneCard() {
		Player player = new Player(new MockPlayerInterface());
		
		player.addCardToCardDeck(new CopperCard());
		
		player.drawCardToHand();		
		assertEquals("Draw card returns wrong number of cards in hand", 1, player.getHandSize());		
	}

	public void testDrawCardToHandWithOneCardAndCardAlreadyInHand() {
		Player player = new Player(new MockPlayerInterface());
		
		player.addCardToCardDeck(new CopperCard());
		player.addCardToCardDeck(new SilverCard());
		
		player.drawCardToHand();		
		player.drawCardToHand();		
		assertEquals("Draw card returns wrong number of cards in hand", 2, player.getHandSize());		
	}

	public void testMoveDiscardPileToCardDeck() {
		Player player = new Player(new MockPlayerInterface());
		
		player.gainCard(new CopperCard());
		player.gainCard(new SilverCard());
		player.gainCard(new GoldCard());
		
		player.moveDiscardPileToCardDeck();
		assertEquals("Move discard pile to card deck returns invalid card deck size", 3, player.getCardDeckSize());
		assertEquals("Move card deck to discard pile returns invalid discard pile size", 0, player.getDiscardPileSize());
	}

	public void testMoveDiscardPileToCardDeckWhenDiscardPileIsEmpty() {
		Player player = new Player(new MockPlayerInterface());
				
		player.moveDiscardPileToCardDeck();
		assertEquals("Move discard pile to card deck returns invalid card deck size", 0, player.getCardDeckSize());
		assertEquals("Move card deck to discard pile returns invalid discard pile size", 0, player.getDiscardPileSize());
	}

	public void testDiscardPlayArea() {
		Player player = new Player(new MockPlayerInterface());
		
		Card card = new CopperCard();
		Card card2 = new SilverCard();
		player.addCardToHand(card);
		player.addCardToHand(card2);
		player.playCard(card);
		player.playCard(card2);
		player.discardPlayArea();
		
		assertEquals("Discard play area returns invalid discard pile size", 2, player.getDiscardPileSize());
	}

	public void testDiscardPlayAreaWhenEmpty() {
		Player player = new Player(new MockPlayerInterface());
		
		player.discardPlayArea();
		
		assertEquals("Discard play area returns invalid discard pile size", 0, player.getDiscardPileSize());
	}

	public void testDiscardCardFromHand() {
		Player player = new Player(new MockPlayerInterface());
		
		Card card = new CopperCard();
		Card card2 = new SilverCard();
		player.addCardToHand(card);
		player.addCardToHand(card2);
		
		player.discardCardFromHand(card);
		player.discardCardFromHand(card2);
		
		assertEquals("Discard card from hand returns invalid discard pile size", 2, player.getDiscardPileSize());
	}

	public void testMoveCardDeckToDiscardPile() {
		Player player = new Player(new MockPlayerInterface());
		
		player.gainCard(new CopperCard());
		player.gainCard(new SilverCard());
		player.gainCard(new GoldCard());
		
		player.moveCardDeckToDiscardPile();
		assertEquals("Move card deck to discard pile returns invalid discard pile size", 3, player.getDiscardPileSize());
		assertEquals("Move discard pile to card deck returns invalid card deck size", 0, player.getCardDeckSize());
	}

	public void testMoveCardDeckToDiscardPileWhenCarkDeckIsEmpty() {
		Player player = new Player(new MockPlayerInterface());
				
		player.moveCardDeckToDiscardPile();
		assertEquals("Move discard pile to card deck returns invalid card deck size", 0, player.getCardDeckSize());
		assertEquals("Move card deck to discard pile returns invalid discard pile size", 0, player.getDiscardPileSize());
	}

	public void testGetHandSize() {
		Player player = new Player(new MockPlayerInterface());
		
		player.addCardToHand(new CopperCard());
		player.addCardToHand(new SilverCard());
		player.addCardToHand(new GoldCard());
		
		assertEquals("Get hand size returns invalid number", 3, player.getHandSize());
	}

	public void testGetHandSizeWhenEmpty() {
		Player player = new Player(new MockPlayerInterface());
		
		assertEquals("Get hand size returns invalid number", 0, player.getHandSize());
	}

	public void testAddCardToDeck() {
		Player player = new Player(new MockPlayerInterface());
		
		player.addCardToCardDeck(new CopperCard());
		player.addCardToCardDeck(new SilverCard());
		player.addCardToCardDeck(new GoldCard());
		
		assertEquals("Add card to deck returns invalid card deck size", 3, player.getCardDeckSize());
	}
	

	public void testAddCardsToDiscardPile() {
		Player player = new Player(new MockPlayerInterface());
		
		Collection<Card> cards = new LinkedList<Card>();
		
		cards.add(new CopperCard());
		cards.add(new SilverCard());
		cards.add(new GoldCard());
		
		player.addCardsToDiscardPile(cards);
		assertEquals("Add card to discard pile returns invalid discard pile size", 3, player.getDiscardPileSize());
	}

	public void testAddCardsToDiscardPileWithEmptyCollection() {
		Player player = new Player(new MockPlayerInterface());
		
		Collection<Card> cards = new LinkedList<Card>();
		
		player.addCardsToDiscardPile(cards);
		assertEquals("Add card to discard pile returns invalid discard pile size", 0, player.getDiscardPileSize());
	}
	
	public void testAddCardsToDiscardPileWithNonEmptyDiscardPile() {
		Player player = new Player(new MockPlayerInterface());
		
		Collection<Card> cards = new LinkedList<Card>();
		cards.add(new CopperCard());
		cards.add(new SilverCard());
		cards.add(new GoldCard());

		player.gainCard(new CopperCard());
		
		player.addCardsToDiscardPile(cards);
		assertEquals("Add card to discard pile returns invalid discard pile size", 4, player.getDiscardPileSize());
	}
	
	public void testDiscardHand() {
		Player player = new Player(new MockPlayerInterface());
		
		player.addCardToHand(new CopperCard());
		player.addCardToHand(new SilverCard());
		player.addCardToHand(new GoldCard());
		
		player.discardHand();		
		assertEquals("Discard hand returns invalid discard pile size", 3, player.getDiscardPileSize());
	}

	public void testDiscardHandWithEmptyHand() {
		Player player = new Player(new MockPlayerInterface());
		
		player.discardHand();		
		assertEquals("Discard hand returns invalid discard pile size", 0, player.getDiscardPileSize());
	}

	public void testDrawNewHand() {
		Player player = new Player(new MockPlayerInterface());
		
		player.addCardToCardDeck(new CopperCard());
		player.addCardToCardDeck(new SilverCard());
		player.addCardToCardDeck(new GoldCard());
		player.addCardToCardDeck(new CopperCard());
		player.addCardToCardDeck(new SilverCard());
		player.addCardToCardDeck(new GoldCard());
		
		player.drawNewHand();		
		assertEquals("Draw new hand returns invalid hand size", 5, player.getHandSize());
		assertEquals("Draw new hand returns invalid card deck size", 1, player.getCardDeckSize());
	}
	
	public void testCountVictoryPointsInCardDeck() {
		Player player = new Player(new MockPlayerInterface());
		
		for (int i=0; i < 10; i++) {
			player.addCardToCardDeck(new CopperCard());
		}
		
		player.addCardToCardDeck(new EstateCard());
		player.addCardToCardDeck(new DuchyCard());
		player.addCardToCardDeck(new ProvinceCard());
		player.addCardToCardDeck(new GardensCard());		
				
		assertEquals("Count victory points return invalid number", 1+3+6+1, player.countVictoryPointsInCardDeck());
	}

	public void testCountTotalCoinsInHand() {
		Player player = new Player(new MockPlayerInterface());
				
		player.addCardToHand(new EstateCard());
		player.addCardToHand(new EstateCard());
		player.addCardToHand(new CopperCard());
		player.addCardToHand(new SilverCard());
		player.addCardToHand(new GoldCard());
				
		assertEquals("Count victory points return invalid number", 1+2+3, player.countTotalCoinsInHand());
	}
*/
}

