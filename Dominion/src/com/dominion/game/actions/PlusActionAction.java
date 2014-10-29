package com.dominion.game.actions;


public class PlusActionAction extends CardAction {
	private int numActions;
	
	public PlusActionAction(int numActions) {
		this.numActions = numActions;
	}

	@Override
	public void execute() {
		player.incrementActions(numActions);
	}
}
