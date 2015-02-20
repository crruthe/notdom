package com.dominion.game.actions;

import com.dominion.game.GameState;

/**
 *
 * If you’ve played 3 or more Actions this turn (counting this): +1 Card; +1 Action.
 * 
 * @author Vomit
 */
public class ConspiratorAction implements CardAction {
	@Override
	public void execute(GameState state) {
		
		// Check num of actions played
		if (state.getTurnState().getNumOfActionsPlayed() >= 3) {
			new PlusCardAction(1).execute(state);
			new PlusActionAction(1).execute(state);			
		}
	}
}
