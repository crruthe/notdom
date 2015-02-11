package com.dominion.game.actions;

import com.dominion.game.GameState;
import com.dominion.game.Player;

public interface ReactionAction extends CardAction {
	public void executeOnPlayer(GameState state, Player player);
}
