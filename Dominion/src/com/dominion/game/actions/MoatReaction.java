package com.dominion.game.actions;

import com.dominion.game.GameState;
import com.dominion.game.Player;

public class MoatReaction implements ReactionAction {
	
	@Override
	public void executeOnPlayer(GameState state, Player player) {
		player.setImmune(true);
	}

	@Override
	public void execute(GameState state) {		
		// Not the current player, so do nothing.
	}	
}
