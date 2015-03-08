package com.dominion.game;

import java.util.List;

import com.dominion.game.actions.MiningVillageAction;
import com.dominion.game.actions.ThroneRoomAction;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.kingdom.MiningVillageCard;

public class MiningVillageActionTest extends ActionTest {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testWithoutTrashing() {
		
		state.addPlayer(new Player(new MockPlayerInterface() {
			@Override
			public boolean chooseIfTrashCard(Card card) {				
				return false;
			}
		}));
		
		MiningVillageCard card = new MiningVillageCard();
		new MiningVillageAction(card).execute(state);
		
		assertTrue(state.getGameBoard().getTrashPile().isEmpty());
		assertEquals(0, state.getTurnState().getTotalCoins());		
	}
	
	public void testWithTrashing() {
		
		state.addPlayer(new Player(new MockPlayerInterface() {
			@Override
			public boolean chooseIfTrashCard(Card card) {				
				return true;
			}
		}));
		
		MiningVillageCard card = new MiningVillageCard();
		new MiningVillageAction(card).execute(state);
		
		assertEquals(1, state.getGameBoard().getTrashPile().count());
		assertEquals(2, state.getTurnState().getTotalCoins());		
	}
	
	public void testWithTrashingAndThroneRoom() {
		
		state.addPlayer(new Player(new MockPlayerInterface() {
			@Override
			public boolean chooseIfTrashCard(Card card) {				
				return true;
			}
			
			@Override
			public ActionCard selectActionCardToPlay(List<Card> cards) {
				return (ActionCard)cards.get(cards.indexOf(new MiningVillageCard()));
			}
		}));
		
		state.getCurrentPlayer().addCardToHand(new MiningVillageCard());
		new ThroneRoomAction().execute(state);
		
		assertEquals(1, state.getGameBoard().getTrashPile().count());
		assertEquals(2, state.getTurnState().getTotalCoins());		
	}	
}
