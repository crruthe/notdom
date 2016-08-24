package com.dominion.game.actions;

import com.dominion.game.GameState;
import com.dominion.game.modifiers.CoppersmithModifier;

/**
 * 
 * @author Vomit
 *
 * Copper produces an extra +1 Coin this turn.
 */
public class CoppersmithAction implements CardAction {
	@Override
	public void execute(GameState state) {
		state.getTurnState().addCardModifier(new CoppersmithModifier());
	}
}
