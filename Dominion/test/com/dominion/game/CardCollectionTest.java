package com.dominion.game;

import java.util.LinkedList;
import java.util.List;

import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.AttackCard;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.ReactionCard;
import com.dominion.game.cards.TreasureCard;
import com.dominion.game.cards.VictoryCard;
import com.dominion.game.cards.basic.CopperCard;
import com.dominion.game.cards.basic.EstateCard;
import com.dominion.game.cards.basic.GoldCard;
import com.dominion.game.cards.basic.ProvinceCard;
import com.dominion.game.cards.basic.SilverCard;
import com.dominion.game.cards.kingdom.GardensCard;
import com.dominion.game.cards.kingdom.MilitiaCard;
import com.dominion.game.cards.kingdom.MoatCard;
import com.dominion.game.cards.kingdom.SpyCard;

import junit.framework.TestCase;

public class CardCollectionTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testAddCards() {
		CardCollection collection = new CardCollection();
		LinkedList<Card> cards = new LinkedList<Card>();
		
		cards.add(new CopperCard());
		cards.add(new EstateCard());
		cards.add(new MoatCard());
		cards.add(new MilitiaCard());
		cards.add(new GardensCard());
		
		collection.addCards(cards);
		
		assertEquals(cards, collection.getCards());
	}

	public void testAddCardToTopAndGetTopCard() {
		CardCollection collection = new CardCollection();
		
		collection.addCardToTop(new MoatCard());
		collection.addCardToTop(new MilitiaCard());
		collection.addCardToTop(new GardensCard());
		
		assertEquals(new GardensCard(), collection.getTopCard());
	}

	public void testClearCards() {
		CardCollection collection = new CardCollection();
		LinkedList<Card> cards = new LinkedList<Card>();
		
		cards.add(new CopperCard());
		cards.add(new EstateCard());
		cards.add(new MoatCard());
		cards.add(new MilitiaCard());
		cards.add(new GardensCard());
		
		collection.addCards(cards);
		
		List<Card> testCards = collection.clearCards();
		
		assertEquals(0, collection.count());
		assertEquals(cards, testCards);
	}

	public void testCount() {
		CardCollection collection = new CardCollection();
		
		collection.addCardToTop(new MoatCard());
		collection.addCardToTop(new MilitiaCard());
		collection.addCardToTop(new GardensCard());
		
		assertEquals(3, collection.count());
	}

	public void testRemoveTopCard() {
		CardCollection collection = new CardCollection();
		
		collection.addCardToTop(new MoatCard());
		collection.addCardToTop(new MilitiaCard());
		collection.addCardToTop(new GardensCard());
		
		assertEquals(new GardensCard(), collection.removeTopCard());
		assertEquals(2, collection.count());		
		assertEquals(new MilitiaCard(), collection.removeTopCard());
		assertEquals(1, collection.count());		
		assertEquals(new MoatCard(), collection.removeTopCard());
		assertEquals(0, collection.count());		
	}

	public void testGetCardsFilterByClass() {
		CardCollection collection = new CardCollection();
		LinkedList<Card> cards = new LinkedList<Card>();
		
		cards.add(new CopperCard());
		cards.add(new SilverCard());
		cards.add(new MoatCard());
		cards.add(new GoldCard());
		cards.add(new EstateCard());
		cards.add(new ProvinceCard());
		cards.add(new MoatCard());
		cards.add(new SpyCard());
		cards.add(new GoldCard());
		cards.add(new MilitiaCard());
		cards.add(new GardensCard());
		
		collection.addCards(cards);
		
		LinkedList<Card> actionCards = new LinkedList<Card>();

		actionCards.add(new MoatCard());
		actionCards.add(new MoatCard());
		actionCards.add(new SpyCard());
		actionCards.add(new MilitiaCard());
		
		assertEquals(actionCards, collection.getCardsFilterByClass(ActionCard.class));
		
		LinkedList<Card> victoryCards = new LinkedList<Card>();

		victoryCards.add(new EstateCard());
		victoryCards.add(new ProvinceCard());
		victoryCards.add(new GardensCard());
		
		assertEquals(victoryCards, collection.getCardsFilterByClass(VictoryCard.class));
	
		LinkedList<Card> attackCards = new LinkedList<Card>();

		attackCards.add(new SpyCard());
		attackCards.add(new MilitiaCard());
		
		assertEquals(attackCards, collection.getCardsFilterByClass(AttackCard.class));

		LinkedList<Card> reactionCards = new LinkedList<Card>();

		reactionCards.add(new MoatCard());
		reactionCards.add(new MoatCard());
		
		assertEquals(reactionCards, collection.getCardsFilterByClass(ReactionCard.class));

	
		LinkedList<Card> treasureCards = new LinkedList<Card>();

		treasureCards.add(new CopperCard());
		treasureCards.add(new SilverCard());
		treasureCards.add(new GoldCard());
		treasureCards.add(new GoldCard());
		
		assertEquals(treasureCards, collection.getCardsFilterByClass(TreasureCard.class));
	}

	public void testRemoveCard() {
		CardCollection collection = new CardCollection();
		LinkedList<Card> cards = new LinkedList<Card>();
		
		cards.add(new CopperCard());
		cards.add(new EstateCard());
		cards.add(new MoatCard());
		cards.add(new MilitiaCard());
		cards.add(new GardensCard());
		
		collection.addCards(cards);
		
		LinkedList<Card> testCards = new LinkedList<Card>();
		
		testCards.add(new CopperCard());
		testCards.add(new EstateCard());
		testCards.add(new MilitiaCard());
		testCards.add(new GardensCard());
		
		collection.removeCard(new MoatCard());
			
		assertEquals(4, collection.count());
		assertEquals(testCards, collection.getCards());
	}
}
