package com.dominion.game.actions;

import java.util.HashMap;

import com.dominion.game.GameState;
import com.dominion.game.interfaces.messages.ActionSelectedMessage;

/**
 * 
 * @author Vomit
 *
 * Choose one: +3 Cards; or +2 Actions.
 */
public class NoblesAction implements CardAction  {
	
	@Override
	public void execute(GameState state) {
		HashMap<String, CardAction> actions = new HashMap<String, CardAction>();
		
		actions.put("+3 Cards", new PlusCardAction(3));
		actions.put("+2 Actions", new PlusActionAction(2));		
		
		String action = null;
		while (!actions.containsKey(action)) {
			action = state.getCurrentPlayer().getCardActionToPlay(actions);
		}

		
    	state.broadcastToAllPlayers(new ActionSelectedMessage(state.getCurrentPlayer(), action));
		
		// Then perform this action
		actions.get(action).execute(state);
	}
}
