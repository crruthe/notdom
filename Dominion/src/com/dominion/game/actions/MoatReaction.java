package com.dominion.game.actions;


public class MoatReaction extends CardAction {
	@Override
	public void execute() {
		player.setImmune(true);		
	}
}
