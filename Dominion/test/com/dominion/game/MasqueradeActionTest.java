package com.dominion.game;

import com.dominion.game.actions.CardAction;
import com.dominion.game.actions.MasqueradeAction;

import junit.framework.TestCase;

public class MasqueradeActionTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testExecuteWithThreePlayers() {
		GameState state = new GameState();
		state.addPlayer(new Player(new MockPlayerInterface()));
		state.addPlayer(new Player(new MockPlayerInterface()));
		state.addPlayer(new Player(new MockPlayerInterface()));
		
		CardAction action = new MasqueradeAction();
		action.execute(state);
	}
}
