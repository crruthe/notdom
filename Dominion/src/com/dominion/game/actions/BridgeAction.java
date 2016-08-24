package com.dominion.game.actions;

import com.dominion.game.GameState;
import com.dominion.game.modifiers.BridgeModifier;

/**
 * 
 * @author Vomit
 *
 * All cards (including cards in players’ hands) cost 1 coin less this turn, but not less than 0 coins.
 */
public class BridgeAction implements CardAction {
	@Override
	public void execute(GameState state) {
		state.getTurnState().addCardModifier(new BridgeModifier());
	}
}
