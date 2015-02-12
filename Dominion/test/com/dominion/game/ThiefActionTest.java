package com.dominion.game;

import java.util.List;

import com.dominion.game.actions.AttackAction;
import com.dominion.game.actions.ThiefAction;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.basic.CopperCard;
import com.dominion.game.cards.basic.EstateCard;
import com.dominion.game.cards.basic.ProvinceCard;
import com.dominion.game.cards.basic.SilverCard;
import junit.framework.TestCase;

public class ThiefActionTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testExecuteAttackOnPlayerTwoTreasure() {
		GameState state = new GameState();
		
		MockPlayerInterface playerInterface = new MockPlayerInterface() {
			@Override
			public boolean chooseIfGainCardThief(Card card) {
				return true;
			}
			
			@Override
			public Card selectCardToTrashThief(List<Card> cards) {
				return cards.get(cards.indexOf(new SilverCard()));
			}
		};
		
		Player player = new Player(playerInterface);
		Player victim = new Player(new MockPlayerInterface());
		
		state.addPlayer(player);
		state.addPlayer(victim);
		
		victim.addCardToCardDeck(new ProvinceCard());
		victim.addCardToCardDeck(new CopperCard());
		victim.addCardToCardDeck(new SilverCard());		
		
		AttackAction action = new ThiefAction();
		action.executeAttackOnPlayer(state, victim);
		
		assertEquals(new SilverCard(), player.drawCard());
	}
	
	public void testExecuteAttackOnPlayerOneTreasure() {
		GameState state = new GameState();
		
		MockPlayerInterface playerInterface = new MockPlayerInterface() {
			@Override
			public boolean chooseIfGainCardThief(Card card) {
				return true;
			}
			
			@Override
			public Card selectCardToTrashThief(List<Card> cards) {
				return cards.get(cards.indexOf(new SilverCard()));
			}
		};
		
		Player player = new Player(playerInterface);
		Player victim = new Player(new MockPlayerInterface());
		
		state.addPlayer(player);
		state.addPlayer(victim);
		
		victim.addCardToCardDeck(new CopperCard());
		victim.addCardToCardDeck(new ProvinceCard());
		victim.addCardToCardDeck(new SilverCard());		
		
		AttackAction action = new ThiefAction();
		action.executeAttackOnPlayer(state, victim);
		
		assertEquals(new SilverCard(), player.drawCard());
	}
	
	public void testExecuteAttackOnPlayerNoTreasure() {
		GameState state = new GameState();
		
		MockPlayerInterface playerInterface = new MockPlayerInterface() {
			@Override
			public boolean chooseIfGainCardThief(Card card) {
				return true;
			}
			
			@Override
			public Card selectCardToTrashThief(List<Card> cards) {
				return cards.get(cards.indexOf(new SilverCard()));
			}
		};
		
		Player player = new Player(playerInterface);
		Player victim = new Player(new MockPlayerInterface());
		
		state.addPlayer(player);
		state.addPlayer(victim);
		
		victim.addCardToCardDeck(new CopperCard());
		victim.addCardToCardDeck(new ProvinceCard());
		victim.addCardToCardDeck(new EstateCard());		
		
		AttackAction action = new ThiefAction();
		action.executeAttackOnPlayer(state, victim);
		
		assertNull(player.drawCard());
	}	
	
	public void testExecuteAttackOnPlayerNoCards() {
		GameState state = new GameState();
		
		Player player = new Player(new MockPlayerInterface());
		Player victim = new Player(new MockPlayerInterface());
		
		state.addPlayer(player);
		state.addPlayer(victim);		
		
		AttackAction action = new ThiefAction();
		action.executeAttackOnPlayer(state, victim);
		
		assertNull(player.drawCard());
		assertNull(victim.drawCard());
		
		assertEquals(0, player.getHandSize());
		assertEquals(0, victim.getHandSize());
	}	
}
