package com.dominion.game;

import java.util.LinkedList;
import com.dominion.game.actions.CardAction;
import com.dominion.game.actions.LibraryAction;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.basic.CopperCard;
import com.dominion.game.cards.basic.DuchyCard;
import com.dominion.game.cards.basic.EstateCard;
import com.dominion.game.cards.basic.ProvinceCard;
import com.dominion.game.cards.basic.SilverCard;
import com.dominion.game.cards.kingdom.WoodcutterCard;

import junit.framework.TestCase;

public class LibraryActionTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testExecuteKeepActionCards() {
		GameState state = new GameState();
		
		MockPlayerInterface playerInterface = new MockPlayerInterface() {
			@Override
			public boolean chooseIfSetAsideCard(Card card) {
				return false;
			}
		};
		
		Player player = new Player(playerInterface);
		
		state.addPlayer(player);
		
		player.addCardToCardDeck(new DuchyCard());
		player.addCardToCardDeck(new CopperCard());
		player.addCardToCardDeck(new EstateCard());
		player.addCardToCardDeck(new CopperCard());
		player.addCardToCardDeck(new EstateCard());
		player.addCardToCardDeck(new SilverCard());
		player.addCardToCardDeck(new WoodcutterCard());
		player.addCardToCardDeck(new WoodcutterCard());
		player.addCardToCardDeck(new ProvinceCard());
				
		CardAction action = new LibraryAction();		
		action.execute(state);		
		
		LinkedList<Card> cards = new LinkedList<Card>();
		cards.add(new ProvinceCard());
		cards.add(new WoodcutterCard());
		cards.add(new WoodcutterCard());
		cards.add(new SilverCard());
		cards.add(new EstateCard());
		cards.add(new CopperCard());
		cards.add(new EstateCard());
		
		assertEquals(cards, playerInterface.getCardHand());
	}
	
	public void testExecuteDiscardActionCards() {
		GameState state = new GameState();
		
		MockPlayerInterface playerInterface = new MockPlayerInterface() {
			@Override
			public boolean chooseIfSetAsideCard(Card card) {
				return true;
			}
		};
		
		Player player = new Player(playerInterface);
		
		state.addPlayer(player);
		
		player.addCardToCardDeck(new DuchyCard());
		player.addCardToCardDeck(new CopperCard());
		player.addCardToCardDeck(new EstateCard());
		player.addCardToCardDeck(new CopperCard());
		player.addCardToCardDeck(new EstateCard());
		player.addCardToCardDeck(new SilverCard());
		player.addCardToCardDeck(new WoodcutterCard());
		player.addCardToCardDeck(new WoodcutterCard());
		player.addCardToCardDeck(new ProvinceCard());
				
		CardAction action = new LibraryAction();		
		action.execute(state);		
		
		LinkedList<Card> cards = new LinkedList<Card>();
		cards.add(new ProvinceCard());
		cards.add(new SilverCard());
		cards.add(new EstateCard());
		cards.add(new CopperCard());
		cards.add(new EstateCard());
		cards.add(new CopperCard());
		cards.add(new DuchyCard());
		
		assertEquals(cards, playerInterface.getCardHand());
	}
	
	public void testRevealWithShuffle() {
		GameState state = new GameState();
		MockPlayerInterface playerInterface = new MockPlayerInterface();
		Player player = new Player(playerInterface);

		state.addPlayer(player);
		
		player.addCardToDiscardPile(new EstateCard());
		player.addCardToDiscardPile(new EstateCard());
		player.addCardToDiscardPile(new EstateCard());
		
		player.addCardToCardDeck(new ProvinceCard());
		player.addCardToCardDeck(new SilverCard());
		player.addCardToCardDeck(new ProvinceCard());
		player.addCardToCardDeck(new ProvinceCard());
		
		CardAction action = new LibraryAction();		
		action.execute(state);
		
		LinkedList<Card> cards = new LinkedList<Card>();
		cards.add(new ProvinceCard());
		cards.add(new ProvinceCard());
		cards.add(new SilverCard());
		cards.add(new ProvinceCard());
		cards.add(new EstateCard());
		cards.add(new EstateCard());
		cards.add(new EstateCard());
		
		assertEquals(cards, playerInterface.getCardHand());
	}	
	
	public void testNoCardsAvailable() {
		GameState state = new GameState();
		MockPlayerInterface playerInterface = new MockPlayerInterface();
		Player player = new Player(playerInterface);

		state.addPlayer(player);
		
		player.addCardToDiscardPile(new EstateCard());
		player.addCardToDiscardPile(new EstateCard());
		
		player.addCardToCardDeck(new ProvinceCard());
		player.addCardToCardDeck(new ProvinceCard());
		player.addCardToCardDeck(new ProvinceCard());
		
		CardAction action = new LibraryAction();		
		action.execute(state);
		
		LinkedList<Card> cards = new LinkedList<Card>();
		cards.add(new ProvinceCard());
		cards.add(new ProvinceCard());
		cards.add(new ProvinceCard());
		cards.add(new EstateCard());
		cards.add(new EstateCard());
		
		assertEquals(cards, playerInterface.getCardHand());
	}		
	
	public void testWithMoreThanSevenCards() {
		GameState state = new GameState();
		MockPlayerInterface playerInterface = new MockPlayerInterface();
		Player player = new Player(playerInterface);

		state.addPlayer(player);
		
		player.addCardToCardDeck(new CopperCard());
		player.addCardToCardDeck(new EstateCard());
		player.addCardToCardDeck(new SilverCard());
		player.addCardToCardDeck(new EstateCard());
		player.addCardToCardDeck(new DuchyCard());
		player.addCardToCardDeck(new CopperCard());
		player.addCardToCardDeck(new EstateCard());
		player.addCardToCardDeck(new CopperCard());
		player.addCardToCardDeck(new EstateCard());
		player.addCardToCardDeck(new SilverCard());
		player.addCardToCardDeck(new EstateCard());
		player.addCardToCardDeck(new ProvinceCard());

		player.drawNewHand();
		player.drawCardToHand();
		player.drawCardToHand();
		
		CardAction action = new LibraryAction();		
		action.execute(state);
		
		LinkedList<Card> cards = new LinkedList<Card>();
		cards.add(new ProvinceCard());
		cards.add(new EstateCard());
		cards.add(new SilverCard());
		cards.add(new EstateCard());
		cards.add(new CopperCard());
		cards.add(new EstateCard());
		cards.add(new CopperCard());
		
		assertEquals(cards, playerInterface.getCardHand());		
	}
}
