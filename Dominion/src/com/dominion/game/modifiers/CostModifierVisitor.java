package com.dominion.game.modifiers;

import com.dominion.game.cards.Card;

public class CostModifierVisitor extends CardModifierVisitor {
	
	private int cost;

	public CostModifierVisitor(Card card) {
		this.cost = card.getCost();
	}
	
	public void visit(CostModifier modifier) {
		this.cost = modifier.getModifiedCost(cost);
	}
	
	public int getCost() {
		return cost;
	}
}
