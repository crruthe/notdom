package com.dominion.game.actions;

import com.dominion.game.GameState;


public class PlusActionAction implements CardAction {
	private int numActions;

	public PlusActionAction(int numActions) {
		this.numActions = numActions;
	}
	
	@Override
	public void execute(GameState state) {
		state.getTurnState().incrementActions(numActions);
	}

	public int getNumActions() {
		return numActions;
	}
}
