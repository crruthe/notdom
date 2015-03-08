package com.dominion.game.actions;

import java.util.HashMap;

import com.dominion.game.GameState;
import com.dominion.game.Player;
import com.dominion.game.interfaces.messages.ActionSelectedMessage;

/**
 * 
 * @author Vomit
 *
 * Choose one: +2 Coins; 
 * or discard your hand, +4 Cards; and each other player with at least 5 cards 
 * in hand discards his hand and draws 4 cards.
 */
public class MinionAction implements CardAction  {
	
	/**
	 * When the player choose minion attack
	 * @author user
	 *
	 */
	class MinionAttackAction extends AttackAction {
		@Override
		public void execute(GameState state) {
			
			// We get to draw 4 cards, regardless of our hand size
			state.getCurrentPlayer().discardHand();
			new PlusCardAction(state.getCurrentPlayer(), 4).execute(state);
			
			super.execute(state);
		}

		@Override
		public void executeAttackOnPlayer(GameState state, Player victim) {		
			if (victim.getHandSize() >= 5) {
				victim.discardHand();
				new PlusCardAction(victim, 4).execute(state);
			}		
		}		
	}
	
	@Override
	public void execute(GameState state) {
		
		HashMap<String, CardAction> actions = new HashMap<String, CardAction>();
		
		actions.put("+2 Coin", new PlusCoinAction(2));
		actions.put("Minion Attack!!!", new MinionAttackAction());
		
		String action = null;
		while (!actions.containsKey(action)) {
			action = state.getCurrentPlayer().getCardActionToPlay(actions);
		}

		
    	state.broadcastToAllPlayers(new ActionSelectedMessage(state.getCurrentPlayer(), action));
		
		// Then perform this action
		actions.get(action).execute(state);
	}
}
