package com.dominion.game;

import com.dominion.game.actions.CourtyardAction;

import java.util.LinkedList;
import java.util.List;

import com.dominion.game.actions.CardAction;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.basic.CopperCard;
import com.dominion.game.cards.basic.EstateCard;
import com.dominion.game.cards.basic.ProvinceCard;
import com.dominion.game.cards.basic.SilverCard;

import junit.framework.TestCase;

public class CourtyardActionTest extends TestCase {

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
			public Card selectCardToPutOnDeck(List<Card> cards) {
				return cards.get(cards.indexOf(new ProvinceCard()));
			}
		};
		
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
		
		player.drawNewHand();
		
		CardAction action = new CourtyardAction();		
		action.execute(state);
		
		LinkedList<Card> cards = new LinkedList<Card>();
		cards.add(new EstateCard());
		cards.add(new SilverCard());
		cards.add(new EstateCard());
		cards.add(new CopperCard());
		assertEquals(cards, playerInterface.getCardHand());
		
		Card card = player.drawCard();
		assertEquals(new ProvinceCard(), card);
	}
}
