package com.dominion.game;

import java.util.LinkedList;

import junit.framework.TestCase;

import com.dominion.game.cards.Card;
import com.dominion.game.cards.TreasureCard;
import com.dominion.game.cards.basic.CopperCard;
import com.dominion.game.cards.basic.DuchyCard;
import com.dominion.game.cards.basic.EstateCard;
import com.dominion.game.cards.basic.GoldCard;
import com.dominion.game.cards.basic.ProvinceCard;
import com.dominion.game.cards.basic.SilverCard;
import com.dominion.game.cards.kingdom.GardensCard;

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

	public void testDrawCardWithTwoCardsInDeck() {
		Player player = new Player(new MockPlayerInterface());
		player.addCardToCardDeck(new CopperCard());
		player.addCardToCardDeck(new SilverCard());

		Card card = player.drawCard();		
		assertEquals("Draw card returns wrong card (first draw)", SilverCard.class, card.getClass());
		
		Card card2 = player.drawCard();		
		assertEquals("Draw card returns wrong card (second draw)", CopperCard.class, card2.getClass());
	}
	
	public void testDrawCardWithOneCardInDiscardPile() {
		Player player = new Player(new MockPlayerInterface());
		player.addCardToDiscardPile(new CopperCard());
		
		Card card = player.drawCard();		
		assertEquals("Draw card returns wrong card", CopperCard.class, card.getClass());
	}
	
	public void testDrawCardWithOneCardInCardDeckAndOneCardInDiscardPile() {
		Player player = new Player(new MockPlayerInterface());
		player.addCardToCardDeck(new CopperCard());
		player.addCardToDiscardPile(new SilverCard());
		
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
		
		player.addCardToDiscardPile(new CopperCard());
		player.addCardToDiscardPile(new SilverCard());
		player.addCardToDiscardPile(new GoldCard());
		
		player.moveDiscardPileToCardDeck();
		assertEquals("Move discard pile to card deck returns invalid card deck size", 3, player.cardDeck.cards.size());
		assertEquals("Move card deck to discard pile returns invalid discard pile size", 0, player.discardPile.cards.size());
	}

	public void testMoveDiscardPileToCardDeckWhenDiscardPileIsEmpty() {
		Player player = new Player(new MockPlayerInterface());
				
		player.moveDiscardPileToCardDeck();
		assertEquals("Move discard pile to card deck returns invalid card deck size", 0, player.cardDeck.cards.size());
		assertEquals("Move card deck to discard pile returns invalid discard pile size", 0, player.discardPile.cards.size());
	}

	public void testDiscardPlayArea() {
		GameState state = new GameState();
		
		Player player = new Player(new MockPlayerInterface());
		state.addPlayer(player);
		
		state.initialise();
		
		TreasureCard card = new CopperCard();
		TreasureCard card2 = new SilverCard();
		player.addCardToHand((Card)card);
		player.addCardToHand((Card)card2);
		
		state.playTreasureCard(card);
		state.playTreasureCard(card2);
		
		player.discardPlayArea();
		
		assertEquals("Discard play area returns invalid discard pile size", 2, player.discardPile.cards.size());
	}

	public void testDiscardPlayAreaWhenEmpty() {
		Player player = new Player(new MockPlayerInterface());
		
		player.discardPlayArea();
		
		assertEquals("Discard play area returns invalid discard pile size", 0, player.discardPile.cards.size());
	}

	public void testDiscardCardFromHand() {
		Player player = new Player(new MockPlayerInterface());
		
		Card card = new CopperCard();
		Card card2 = new SilverCard();
		player.addCardToHand(card);
		player.addCardToHand(card2);
		
		player.discardCardFromHand(card);
		player.discardCardFromHand(card2);
		
		assertEquals("Discard card from hand returns invalid discard pile size", 2, player.discardPile.cards.size());
	}

	public void testMoveCardDeckToDiscardPile() {
		Player player = new Player(new MockPlayerInterface());
		
		player.addCardToDiscardPile(new CopperCard());
		player.addCardToDiscardPile(new SilverCard());
		player.addCardToDiscardPile(new GoldCard());
		
		player.discardEntireCardDeck();
		
		assertEquals("Move card deck to discard pile returns invalid discard pile size", 3, player.discardPile.cards.size());
		assertEquals("Move discard pile to card deck returns invalid card deck size", 0, player.cardDeck.cards.size());
	}

	public void testMoveCardDeckToDiscardPileWhenCardDeckIsEmpty() {
		Player player = new Player(new MockPlayerInterface());
				
		player.discardEntireCardDeck();
		
		assertEquals("Move discard pile to card deck returns invalid card deck size", 0, player.cardDeck.cards.size());
		assertEquals("Move card deck to discard pile returns invalid discard pile size", 0, player.discardPile.cards.size());
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
		
		assertEquals("Add card to deck returns invalid card deck size", 3, player.cardDeck.cards.size());
	}
	

	public void testAddCardsToDiscardPile() {
		Player player = new Player(new MockPlayerInterface());
		
		LinkedList<Card> cards = new LinkedList<Card>();
		
		cards.add(new CopperCard());
		cards.add(new SilverCard());
		cards.add(new GoldCard());
		
		player.addCardsToDiscardPile(cards);
		assertEquals("Add card to discard pile returns invalid discard pile size", 3, player.discardPile.cards.size());
	}

	public void testAddCardsToDiscardPileWithEmptyCollection() {
		Player player = new Player(new MockPlayerInterface());
		
		LinkedList<Card> cards = new LinkedList<Card>();
		
		player.addCardsToDiscardPile(cards);
		assertEquals("Add card to discard pile returns invalid discard pile size", 0, player.discardPile.cards.size());
	}
	
	public void testAddCardsToDiscardPileWithNonEmptyDiscardPile() {
		Player player = new Player(new MockPlayerInterface());
		
		LinkedList<Card> cards = new LinkedList<Card>();
		cards.add(new CopperCard());
		cards.add(new SilverCard());
		cards.add(new GoldCard());

		player.addCardToDiscardPile(new CopperCard());
		
		player.addCardsToDiscardPile(cards);
		assertEquals("Add card to discard pile returns invalid discard pile size", 4, player.discardPile.cards.size());
	}
	
	public void testDiscardHand() {
		Player player = new Player(new MockPlayerInterface());
		
		player.addCardToHand(new CopperCard());
		player.addCardToHand(new SilverCard());
		player.addCardToHand(new GoldCard());
		
		player.discardHand();		
		assertEquals("Discard hand returns invalid discard pile size", 3, player.discardPile.cards.size());
	}

	public void testDiscardHandWithEmptyHand() {
		Player player = new Player(new MockPlayerInterface());
		
		player.discardHand();		
		assertEquals("Discard hand returns invalid discard pile size", 0, player.discardPile.cards.size());
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
		assertEquals("Draw new hand returns invalid card deck size", 1, player.cardDeck.cards.size());
	}
	
	public void testCountVictoryPoints() {
		Player player = new Player(new MockPlayerInterface());
		
		for (int i=0; i < 10; i++) {
			player.addCardToCardDeck(new CopperCard());
		}
		
		player.addCardToCardDeck(new EstateCard());
		player.addCardToCardDeck(new DuchyCard());
		player.addCardToCardDeck(new ProvinceCard());
		player.addCardToCardDeck(new GardensCard());		
		
		assertEquals("Count victory points return invalid number", 1+3+6+1, player.countVictoryPoints(player.getAllCards()));
	}
}

