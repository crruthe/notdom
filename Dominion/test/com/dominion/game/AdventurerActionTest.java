package com.dominion.game;

import java.util.LinkedList;
import com.dominion.game.actions.AdventurerAction;
import com.dominion.game.actions.CardAction;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.basic.CopperCard;
import com.dominion.game.cards.basic.EstateCard;
import com.dominion.game.cards.basic.ProvinceCard;
import com.dominion.game.cards.basic.SilverCard;
import junit.framework.TestCase;

public class AdventurerActionTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testExecute() {
		GameState state = new GameState();
		MockPlayerInterface playerInterface = new MockPlayerInterface();
		Player player = new Player(playerInterface);

		state.addPlayer(player);
		
		player.addCardToCardDeck(new EstateCard());
		player.addCardToCardDeck(new CopperCard());
		player.addCardToCardDeck(new EstateCard());
		player.addCardToCardDeck(new CopperCard());
		player.addCardToCardDeck(new EstateCard());
		player.addCardToCardDeck(new SilverCard());
		player.addCardToCardDeck(new EstateCard());
		player.addCardToCardDeck(new ProvinceCard());
		
		CardAction action = new AdventurerAction();		
		action.execute(state);
		
		LinkedList<Card> cards = new LinkedList<Card>();
		cards.add(new SilverCard());
		cards.add(new CopperCard());
		
		assertEquals(cards, playerInterface.getCardHand());
		
	}
	
	public void testRevealWithShuffle() {
		GameState state = new GameState();
		MockPlayerInterface playerInterface = new MockPlayerInterface();
		Player player = new Player(playerInterface);

		state.addPlayer(player);
		
		player.addCardToDiscardPile(new EstateCard());
		player.addCardToDiscardPile(new CopperCard());
		player.addCardToDiscardPile(new EstateCard());
		player.addCardToDiscardPile(new CopperCard());
		
		player.addCardToCardDeck(new ProvinceCard());
		player.addCardToCardDeck(new SilverCard());
		player.addCardToCardDeck(new ProvinceCard());
		player.addCardToCardDeck(new ProvinceCard());
		
		CardAction action = new AdventurerAction();		
		action.execute(state);
		
		LinkedList<Card> cards = new LinkedList<Card>();
		cards.add(new SilverCard());
		cards.add(new CopperCard());
		
		assertEquals(cards, playerInterface.getCardHand());
	}	
	
	public void testNoTreasureCardsAvailable() {
		GameState state = new GameState();
		MockPlayerInterface playerInterface = new MockPlayerInterface();
		Player player = new Player(playerInterface);

		state.addPlayer(player);
		
		player.addCardToDiscardPile(new EstateCard());
		player.addCardToDiscardPile(new EstateCard());
		
		player.addCardToCardDeck(new ProvinceCard());
		player.addCardToCardDeck(new ProvinceCard());
		player.addCardToCardDeck(new ProvinceCard());
		
		CardAction action = new AdventurerAction();		
		action.execute(state);
		
		LinkedList<Card> cards = new LinkedList<Card>();
		
		assertEquals(cards, playerInterface.getCardHand());
	}	
}
