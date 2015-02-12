package com.dominion.game;

import java.util.LinkedList;
import java.util.List;

import com.dominion.game.actions.CellarAction;
import com.dominion.game.actions.CardAction;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.basic.CopperCard;
import com.dominion.game.cards.basic.DuchyCard;
import com.dominion.game.cards.basic.EstateCard;
import com.dominion.game.cards.basic.ProvinceCard;
import com.dominion.game.cards.basic.SilverCard;

import junit.framework.TestCase;

public class CellarActionTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testExecute() {
		GameState state = new GameState();
		
		MockPlayerInterface playerInterface = new MockPlayerInterface() {
			@Override
			public Card selectCardToDiscard(List<Card> cards) {
				// Discard 3 out of the 5 cards
				if (cards.size() > 2) {
					System.out.println(cards.get(0));
					return cards.get(0);					
				}
				return null;
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
		player.addCardToCardDeck(new EstateCard());
		player.addCardToCardDeck(new ProvinceCard());
		
		player.drawNewHand();
		
		CardAction action = new CellarAction();		
		action.execute(state);		
		
		assertEquals(5, player.getHandSize());
		
		LinkedList<Card> cards = new LinkedList<Card>();
		cards.add(new EstateCard());
		cards.add(new CopperCard());
		cards.add(new EstateCard());
		cards.add(new CopperCard());
		cards.add(new DuchyCard());
		
		assertEquals(cards, playerInterface.getCardHand());
	}
}
