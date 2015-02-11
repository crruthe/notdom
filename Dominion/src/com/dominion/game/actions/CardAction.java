package com.dominion.game.actions;

import com.dominion.game.GameState;

/**
 * An action will invoke the game state based on the given context.
 *
 * It is up to the individual action to identified it's required context.
 * 
 * e.g. new PlusBuyAction(Player affectedPlayer, int numOfBuys)
 */
public interface CardAction {	
	public void execute(GameState state);
}
