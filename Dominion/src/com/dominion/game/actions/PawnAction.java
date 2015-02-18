package com.dominion.game.actions;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;

import com.dominion.game.GameState;
import com.dominion.game.cards.Card;

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
		List<CardAction> selectedActions = new LinkedList<CardAction>();
		
		actions.put("+1 Card", new PlusCardAction(1));
		actions.put("+1 Action", new PlusActionAction(1));
		actions.put("+1 Buy", new PlusBuyAction(1));
		actions.put("+1 Coin", new PlusCoinAction(1));
		
		// Pick two actions to perform first
		for (int i = 0; i < 2; i++) {
			CardAction action = state.getCurrentPlayer().getCardActionToPlay(actions);
			selectedActions.add(action);
			
			// Cannot repeat the action, so remove it
			actions.values().remove(action);			
		}		
		
		// Then perform those actions
		for (CardAction action : selectedActions) {
			action.execute(state);
		}
	}
}
