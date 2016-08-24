package com.dominion.game.modifiers;

public class BridgeModifier extends CostModifier {

	/**
	 * Reduce the cost of the card by one, but no less than 0 
	 */
	@Override
	public int getModifiedCost(int cost) {
		return Math.max(cost - 1, 0);
	}
}
