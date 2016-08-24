package com.dominion.game;

import com.dominion.game.actions.BridgeAction;
import com.dominion.game.actions.CardAction;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.basic.ProvinceCard;
import com.dominion.game.cards.basic.SilverCard;

public class BridgeActionTest extends ActionTest {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testOneBridge() {
		
		state.addPlayer(new Player(new MockPlayerInterface()));
		
		CardAction action = new BridgeAction();		
		action.execute(state);
		
		Card card = new SilverCard();
		
		assertEquals(2, state.getModifiedCost(card));
	}
	
	public void testMultipleBridges() {
		state.addPlayer(new Player(new MockPlayerInterface()));		
		
		CardAction action = new BridgeAction();		
		action.execute(state);		

		CardAction action2 = new BridgeAction();		
		action2.execute(state);		
		
		Card card = new SilverCard();
		
		assertEquals(1, state.getModifiedCost(card));

		Card card1 = new ProvinceCard();
		
		assertEquals(6, state.getModifiedCost(card1));
	}	
}
