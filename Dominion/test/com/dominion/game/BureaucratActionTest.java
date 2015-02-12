package com.dominion.game;

import com.dominion.game.actions.AttackAction;
import com.dominion.game.actions.BureaucratAction;
import com.dominion.game.actions.CardAction;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.basic.CopperCard;
import com.dominion.game.cards.basic.EstateCard;
import com.dominion.game.cards.basic.ProvinceCard;
import com.dominion.game.cards.basic.SilverCard;

import junit.framework.TestCase;

public class BureaucratActionTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testExecute() {
		GameState state = new GameState();
		state.initialise();
		
		Player player = new Player(new MockPlayerInterface());
		
		state.addPlayer(player);
		
		player.addCardToCardDeck(new EstateCard());
		player.addCardToCardDeck(new CopperCard());
		player.addCardToCardDeck(new EstateCard());
		player.addCardToCardDeck(new CopperCard());
		player.addCardToCardDeck(new EstateCard());
		player.addCardToCardDeck(new SilverCard());
		player.addCardToCardDeck(new EstateCard());
		player.addCardToCardDeck(new ProvinceCard());
		
		CardAction action = new BureaucratAction();		
		action.execute(state);
		
		
		assertEquals(new SilverCard(), player.drawCard());
	}

	public void testExecuteAttackOnPlayer() {
		GameState state = new GameState();
		
		MockPlayerInterface playerInterface = new MockPlayerInterface() {
			@Override
			public Card selectVictoryCardToReveal(java.util.List<Card> cards) {
				Card card = cards.get(cards.indexOf(new ProvinceCard()));
				System.out.println(card);
				return card;
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
		
		AttackAction action = new BureaucratAction();		
		action.executeAttackOnPlayer(state, player);

		Card card = player.drawCard();
		
		assertEquals(new ProvinceCard(), card);
	}
}
