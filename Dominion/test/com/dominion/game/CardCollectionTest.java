package com.dominion.game;

import java.util.Collections;
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
		
		List<Card> actualCards1 = collection.getCardsFilterByClass(ActionCard.class);
		Collections.sort(actionCards);
		Collections.sort(actualCards1);
		
		assertEquals(actionCards, actualCards1);
		
		LinkedList<Card> victoryCards = new LinkedList<Card>();

		victoryCards.add(new EstateCard());
		victoryCards.add(new ProvinceCard());
		victoryCards.add(new GardensCard());
		
		List<Card> actualCards2 = collection.getCardsFilterByClass(VictoryCard.class);
		Collections.sort(victoryCards);
		Collections.sort(actualCards2);
		
		assertEquals(victoryCards, actualCards2);
	
		LinkedList<Card> attackCards = new LinkedList<Card>();

		attackCards.add(new SpyCard());
		attackCards.add(new MilitiaCard());
		
		List<Card> actualCards3 = collection.getCardsFilterByClass(AttackCard.class);
		Collections.sort(attackCards);
		Collections.sort(actualCards3);
		
		assertEquals(attackCards, actualCards3);

		LinkedList<Card> reactionCards = new LinkedList<Card>();

		reactionCards.add(new MoatCard());
		reactionCards.add(new MoatCard());
		
		List<Card> actualCards4 = collection.getCardsFilterByClass(ReactionCard.class);
		Collections.sort(reactionCards);
		Collections.sort(actualCards4);
		
		assertEquals(reactionCards, actualCards4);

	
		LinkedList<Card> treasureCards = new LinkedList<Card>();

		treasureCards.add(new CopperCard());
		treasureCards.add(new SilverCard());
		treasureCards.add(new GoldCard());
		treasureCards.add(new GoldCard());
		
		List<Card> actualCards5 = collection.getCardsFilterByClass(TreasureCard.class);
		Collections.sort(reactionCards);
		Collections.sort(actualCards5);
		
		assertEquals(treasureCards, actualCards5);
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
