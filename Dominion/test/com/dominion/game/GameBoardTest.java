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
import com.dominion.game.cards.basic.CurseCard;
import com.dominion.game.cards.basic.DuchyCard;
import com.dominion.game.cards.basic.EstateCard;
import com.dominion.game.cards.basic.SilverCard;
import com.dominion.game.cards.kingdom.*;

import junit.framework.TestCase;

public class GameBoardTest extends TestCase {

	private GameBoard gameBoard;
	
	protected void setUp() throws Exception {
		LinkedList<Class<? extends Card>> cardList = new LinkedList<Class<? extends Card>>();
		
		cardList.add(WoodcutterCard.class);
		cardList.add(WitchCard.class);		
		cardList.add(MoatCard.class);		
		cardList.add(GardensCard.class);		
		cardList.add(CellarCard.class);		
		cardList.add(MilitiaCard.class);	
		cardList.add(AdventurerCard.class);
		cardList.add(MoatCard.class);
		cardList.add(SpyCard.class);
		cardList.add(CourtyardCard.class);
		
		gameBoard = new GameBoard();
		gameBoard.setup(cardList, 2);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testAddToTrashPile() {
		CardCollection cards = new CardCollection();
		
		cards.addCardToTop(new CopperCard());
		cards.addCardToTop(new EstateCard());
		cards.addCardToTop(new MoatCard());
		cards.addCardToTop(new MilitiaCard());
		cards.addCardToTop(new GardensCard());
		
		GameBoard board = new GameBoard();
		
		board.addToTrashPile(new CopperCard());
		board.addToTrashPile(new EstateCard());
		board.addToTrashPile(new MoatCard());
		board.addToTrashPile(new MilitiaCard());
		board.addToTrashPile(new GardensCard());
		
		assertEquals(cards.getCards(), board.getTrashPile().getCards());
	}

	public void testCountNumberOfEmptyStacks() {		
		assertEquals(0, gameBoard.countNumberOfEmptyStacks());

		for (int i = 0; i < 8; i++) {
			gameBoard.removeCardFromSupply(EstateCard.class);
		}
		
		assertEquals(1, gameBoard.countNumberOfEmptyStacks());

		for (int i = 0; i < 10; i++) {
			gameBoard.removeCardFromSupply(CurseCard.class);
		}
		
		assertEquals(2, gameBoard.countNumberOfEmptyStacks());

		for (int i = 0; i < 5; i++) {
			gameBoard.removeCardFromSupply(MilitiaCard.class);
		}
		
		assertEquals(2, gameBoard.countNumberOfEmptyStacks());

		for (int i = 0; i < 5; i++) {
			gameBoard.removeCardFromSupply(CellarCard.class);
		}
		
		assertEquals(2, gameBoard.countNumberOfEmptyStacks());

		for (int i = 0; i < 20; i++) {
			gameBoard.removeCardFromSupply(CopperCard.class);
		}
		
		assertEquals(2, gameBoard.countNumberOfEmptyStacks());

		for (int i = 0; i < 10; i++) {
			gameBoard.removeCardFromSupply(AdventurerCard.class);
		}
		
		assertEquals(3, gameBoard.countNumberOfEmptyStacks());
	}

	public void testIsStackEmpty() {
		GameBoard board = new GameBoard();
		board.setupRandom(2);
		
		for (int i = 0; i < 8; i++) {
			assertEquals(new EstateCard(), board.removeCardFromSupply(EstateCard.class));
		}
		
		assertTrue(board.isStackEmpty(EstateCard.class));
	}

	public void testListCardsFilterByCost() {
		CardCollection cards = new CardCollection();
		
		cards.addCardToTop(new WoodcutterCard());
		cards.addCardToTop(new MoatCard());
		cards.addCardToTop(new MilitiaCard());
		cards.addCardToTop(new GardensCard());
		cards.addCardToTop(new CellarCard());
		cards.addCardToTop(new CourtyardCard());
		cards.addCardToTop(new CopperCard());
		cards.addCardToTop(new SilverCard());
		cards.addCardToTop(new EstateCard());
		cards.addCardToTop(new CurseCard());
		cards.addCardToTop(new SpyCard());
		
		List<Card> expectedCards = cards.getCards();
		List<Card> actualCards = gameBoard.listCardsFilterByCost(4);
		
		Collections.sort(expectedCards);
		Collections.sort(actualCards);
		
		assertEquals(expectedCards, actualCards);
	}

	public void testListCardsFilterByClassAndCost() {
		List<Card> actualCards;
		CardCollection cards;
		
		cards = new CardCollection();		
		cards.addCardToTop(new GardensCard());
		cards.addCardToTop(new EstateCard());
		cards.addCardToTop(new DuchyCard());
		
		List<Card> victoryCards = cards.getCards();
		actualCards = gameBoard.listCardsFilterByClassAndCost(VictoryCard.class,6);
		
		Collections.sort(victoryCards);
		Collections.sort(actualCards);
		
		assertEquals(victoryCards, actualCards);		

		cards = new CardCollection();		
		cards.addCardToTop(new CopperCard());
		cards.addCardToTop(new SilverCard());
		
		List<Card> treasureCards = cards.getCards();
		actualCards = gameBoard.listCardsFilterByClassAndCost(TreasureCard.class,3);
		
		Collections.sort(treasureCards);
		Collections.sort(actualCards);
		
		assertEquals(treasureCards, actualCards);		

		cards = new CardCollection();		
		cards.addCardToTop(new WoodcutterCard());
		cards.addCardToTop(new MoatCard());
		cards.addCardToTop(new MilitiaCard());
		cards.addCardToTop(new CellarCard());
		cards.addCardToTop(new CourtyardCard());
		cards.addCardToTop(new SpyCard());
		
		List<Card> actionCards = cards.getCards();
		actualCards = gameBoard.listCardsFilterByClassAndCost(ActionCard.class,4);
		
		Collections.sort(actionCards);
		Collections.sort(actualCards);
		
		assertEquals(actionCards, actualCards);		

		cards = new CardCollection();		
		cards.addCardToTop(new MilitiaCard());
		cards.addCardToTop(new WitchCard());
		cards.addCardToTop(new SpyCard());
		
		List<Card> attackCards = cards.getCards();
		actualCards = gameBoard.listCardsFilterByClassAndCost(AttackCard.class,5);
		
		Collections.sort(attackCards);
		Collections.sort(actualCards);
		
		assertEquals(attackCards, actualCards);		

		cards = new CardCollection();		
		cards.addCardToTop(new MoatCard());
		
		List<Card> reactionCards = cards.getCards();
		actualCards = gameBoard.listCardsFilterByClassAndCost(ReactionCard.class,3);
		
		Collections.sort(reactionCards);
		Collections.sort(actualCards);
		
		assertEquals(reactionCards, actualCards);		

		cards = new CardCollection();		
		
		List<Card> noCards = cards.getCards();
		actualCards = gameBoard.listCardsFilterByClassAndCost(VictoryCard.class,1);
		
		Collections.sort(noCards);
		Collections.sort(actualCards);
		
		assertEquals(noCards, actualCards);		
	}

	public void testRemoveCardFromSupply() {
		GameBoard board = new GameBoard();
		board.setupRandom(2);
		
		for (int i = 0; i < 8; i++) {
			assertEquals(new EstateCard(), board.removeCardFromSupply(EstateCard.class));
		}
		
		assertNull(board.removeCardFromSupply(EstateCard.class));
	}

	public void testSetup() {
		GameBoard board = new GameBoard();
		board.setupRandom(2);
	}
}
