package com.dominion.game;

import com.dominion.game.actions.AttackAction;
import com.dominion.game.actions.SpyAction;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.basic.SilverCard;
import com.dominion.game.cards.kingdom.WitchCard;

import junit.framework.TestCase;

public class SpyActionTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testPlayerWithoutCardsDoesNotReveal() {
		GameState state = new GameState();
		state.initialise();
		
		MockPlayerInterface attackerInterface = new MockPlayerInterface() {
			@Override
			public boolean chooseIfDiscardCard(Card card) {
				return false;
			}
			
			@Override
			public void notifyCardRevealed(Player player, Card card) {
				assertEquals(new WitchCard(), card);
			}
		};
		
		MockPlayerInterface victimInterface = new MockPlayerInterface() {
			@Override
			public void notifyCardRevealed(Player player, Card card) {
				fail("Player without cards cannot reveal");
			}
		};
		
		Player attacker = new Player(attackerInterface);
		Player victim = new Player(victimInterface);
		
		state.addPlayer(attacker);
		state.addPlayer(victim);
		
		attacker.addCardToCardDeck(new WitchCard());
		
		AttackAction action = new SpyAction();		
		action.executeAttackOnPlayer(state, victim);
		
		assertNull(victimInterface.getDiscardCard());
		assertNull(attackerInterface.getDiscardCard());
	}
	
	public void testNoDiscard() {
		GameState state = new GameState();
		state.initialise();
		
		MockPlayerInterface attackerInterface = new MockPlayerInterface() {
			@Override
			public boolean chooseIfDiscardCard(Card card) {
				return false;
			}
		};

		MockPlayerInterface victimInterface = new MockPlayerInterface();
		
		Player attacker = new Player(attackerInterface);
		Player victim = new Player(victimInterface);
		
		state.addPlayer(attacker);
		state.addPlayer(victim);
		
		attacker.addCardToCardDeck(new WitchCard());
		victim.addCardToCardDeck(new SilverCard());
		
		AttackAction action = new SpyAction();		
		action.execute(state);
		
		assertNull(victimInterface.getDiscardCard());
		assertNull(attackerInterface.getDiscardCard());
		assertEquals(1, attackerInterface.getDeckSize());
		assertEquals(1, victimInterface.getDeckSize());
	}

	public void testDiscard() {
		GameState state = new GameState();
		state.initialise();
		
		MockPlayerInterface attackerInterface = new MockPlayerInterface() {
			@Override
			public boolean chooseIfDiscardCard(Card card) {
				return true;
			}
		};
		
		MockPlayerInterface victimInterface = new MockPlayerInterface();
		
		Player attacker = new Player(attackerInterface);
		Player victim = new Player(victimInterface);
		
		state.addPlayer(attacker);
		state.addPlayer(victim);
		
		attacker.addCardToCardDeck(new WitchCard());
		victim.addCardToCardDeck(new SilverCard());
		
		AttackAction action = new SpyAction();		
		action.execute(state);
		
		assertEquals(new WitchCard(), attackerInterface.getDiscardCard());
		assertEquals(new SilverCard(), victimInterface.getDiscardCard());
		assertEquals(0, attackerInterface.getDeckSize());
		assertEquals(0, victimInterface.getDeckSize());
	}
}
