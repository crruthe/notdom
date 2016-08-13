package com.dominion.game;

import com.dominion.game.actions.PlusCoinAction;

import junit.framework.TestCase;

public class PlusCoinActionTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testExecute() {
		GameState state = new GameState();
		state.initialise();

		PlusCoinAction action1 = new PlusCoinAction(1);
		PlusCoinAction action5 = new PlusCoinAction(5);
		
		assertEquals(0, state.turnState.totalCoins);
		
		action1.execute(state);
		
		assertEquals(1, state.turnState.totalCoins);

		action1.execute(state);
		
		assertEquals(2, state.turnState.totalCoins);
		
		action5.execute(state);
		
		assertEquals(7, state.turnState.totalCoins);		
	}
}
