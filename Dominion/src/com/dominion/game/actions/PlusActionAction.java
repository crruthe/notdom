package com.dominion.game.actions;

import com.dominion.game.Player;


public class PlusActionAction implements CardAction {
	private int numActions;

	public PlusActionAction(int numActions) {
		this.numActions = numActions;
	}
	
	@Override
	public void execute(Player player) {
		player.incrementActions(numActions);
	}

	public int getNumActions() {
		return numActions;
	}
}
