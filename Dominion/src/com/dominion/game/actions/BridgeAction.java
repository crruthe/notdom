package com.dominion.game.actions;

import com.dominion.game.GameState;
import com.dominion.game.modifiers.BridgeModifier;

/**
 * 
 * @author Vomit
 *
 * Trash a card from your hand. Gain a card costing up to 2 Coins more than the trashed card.
 */
public class BridgeAction implements CardAction {
	@Override
	public void execute(GameState state) {
		state.getTurnState().addModifier(new BridgeModifier());
	}
}
