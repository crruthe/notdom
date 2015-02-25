package com.dominion.game.actions;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.dominion.game.GameState;
import com.dominion.game.interfaces.messages.ActionSelectedMessage;

/**
 * 
 * @author Vomit
 *
 * Choose two: +1 Card; +1 Action; +1 Buy; +1 Coin. (The choices must be different.)
 */
public class PawnAction implements CardAction {
	@Override
	public void execute(GameState state) {
		
		HashMap<String, CardAction> actions = new HashMap<String, CardAction>();
		List<String> selectedActions = new LinkedList<String>();
		
		actions.put("+1 Card", new PlusCardAction(1));
		actions.put("+1 Action", new PlusActionAction(1));
		actions.put("+1 Buy", new PlusBuyAction(1));
		actions.put("+1 Coin", new PlusCoinAction(1));

		// Pick two actions to perform first
		for (int i = 0; i < 2; i++) {
			String action = null;
			while (!actions.containsKey(action)) {
				action = state.getCurrentPlayer().getCardActionToPlay(actions);
			}
			selectedActions.add(action);
			
			// Cannot repeat the action, so remove it
			actions.values().remove(action);

	    	state.broadcastToAllPlayers(new ActionSelectedMessage(state.getCurrentPlayer(), action));
		}		
		
		// Then perform those actions
		for (String action : selectedActions) {
			actions.get(action).execute(state);
		}
	}
}
