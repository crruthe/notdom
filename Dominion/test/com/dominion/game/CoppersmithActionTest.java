package com.dominion.game;

import com.dominion.game.actions.CardAction;
import com.dominion.game.actions.CoppersmithAction;
import com.dominion.game.cards.basic.CopperCard;
import com.dominion.game.cards.basic.GoldCard;
import com.dominion.game.cards.basic.SilverCard;

public class CoppersmithActionTest extends ActionTest {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testMultipleCoppers() {
		
		state.addPlayer(new Player(new MockPlayerInterface()));
		
		CardAction action = new CoppersmithAction();		
		action.execute(state);		
		
		state.playTreasureCard(new CopperCard());
		
		assertEquals(2, state.getTurnState().getTotalCoins()); 

		state.playTreasureCard(new CopperCard());
		
		assertEquals(4, state.getTurnState().getTotalCoins()); 

		state.playTreasureCard(new CopperCard());
		
		assertEquals(6, state.getTurnState().getTotalCoins()); 

	}
	
	public void testNonCopperTreasureCards() {
		state.addPlayer(new Player(new MockPlayerInterface()));
		
		CardAction action = new CoppersmithAction();		
		action.execute(state);		
		
		state.playTreasureCard(new SilverCard());
		
		assertEquals(2, state.getTurnState().getTotalCoins()); 

		state.playTreasureCard(new GoldCard());
		
		assertEquals(5, state.getTurnState().getTotalCoins());
	}
	
	public void testMultipleCoppersmiths() {
		state.addPlayer(new Player(new MockPlayerInterface()));		
		
		CardAction action = new CoppersmithAction();		
		action.execute(state);		

		CardAction action2 = new CoppersmithAction();		
		action2.execute(state);		
		
		state.playTreasureCard(new CopperCard());
		
		assertEquals(3, state.getTurnState().getTotalCoins()); 

		state.playTreasureCard(new CopperCard());
		
		assertEquals(6, state.getTurnState().getTotalCoins()); 

		state.playTreasureCard(new CopperCard());
		
		assertEquals(9, state.getTurnState().getTotalCoins());
	}	
}
