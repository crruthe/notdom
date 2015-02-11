package com.dominion.game.actions;

import com.dominion.game.Player;

public class MoatReaction implements CardAction {
	@Override
	public void execute(Player player) {
		player.setImmune(true);
	}	
}
