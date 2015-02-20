package com.dominion.game;

import com.dominion.game.actions.BridgeAction;
import com.dominion.game.actions.CardAction;
import com.dominion.game.actions.CoppersmithAction;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.TreasureCard;
import com.dominion.game.cards.basic.CopperCard;
import com.dominion.game.cards.basic.GoldCard;
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
		Card mCard = state.modifyCard(card);
		
		assertEquals(2, mCard.getCost());
	}
	
	public void testMultipleBridges() {
		state.addPlayer(new Player(new MockPlayerInterface()));		
		
		CardAction action = new BridgeAction();		
		action.execute(state);		

		CardAction action2 = new BridgeAction();		
		action2.execute(state);		
		
		Card card = new SilverCard();
		Card mCard = state.modifyCard(card);
		
		assertEquals(1, mCard.getCost());

		Card card1 = new ProvinceCard();
		Card mCard1 = state.modifyCard(card1);
		
		assertEquals(6, mCard1.getCost());
	}	
}
