package com.dominion.game.actions;

import java.util.HashMap;

import com.dominion.game.GameState;
import com.dominion.game.interfaces.messages.ActionSelectedMessage;

/**
 * 
 * @author Vomit
 *
 * Choose one: +2 Cards; or +2 Coins; or trash 2 cards from your hand.
 */
public class StewardAction implements CardAction {
	@Override
	public void execute(GameState state) {
		
		HashMap<String, CardAction> actions = new HashMap<String, CardAction>();
		
		actions.put("+2 Card", new PlusCardAction(2));
		actions.put("+2 Coin", new PlusCoinAction(2));
		actions.put("Trash 2 Cards", new TrashCardAction(2, true));		
		
		String action = null;
		while (!actions.containsKey(action)) {
			action = state.getCurrentPlayer().getCardActionToPlay(actions);
		}
		
    	state.broadcastToAllPlayers(new ActionSelectedMessage(state.getCurrentPlayer(), action));
		
		// Then perform this action
		actions.get(action).execute(state);
	}
}
