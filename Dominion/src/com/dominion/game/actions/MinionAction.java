package com.dominion.game.actions;

import java.util.HashMap;
import java.util.Map.Entry;

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
public class MinionAction extends AttackAction  {
	
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
			
			// Already allowed for reaction, no need to repeat
			attackOtherPlayers(state);
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
		getReactionToAttack(state);
		
		HashMap<String, CardAction> actions = new HashMap<String, CardAction>();
		
		actions.put("+2 Coin", new PlusCoinAction(2));
		actions.put("Minion Attack!!!", new MinionAttackAction());
		
		CardAction action = state.getCurrentPlayer().getCardActionToPlay(actions);

		// Broadcast this decision
		// TODO: code smell... inefficient
		for (Entry<String, CardAction> entry : actions.entrySet()) {
	        if (action.equals(entry.getValue())) {
	        	state.broadcastToAllPlayers(new ActionSelectedMessage(state.getCurrentPlayer(), entry.getKey()));
	        }
	    }
		
		// Then perform this action
		action.execute(state);
	}

	@Override
	public void executeAttackOnPlayer(GameState state, Player victim) {
		// Do nothing, this is handled by MinionAttackAction		
	}
}
