package com.dominion.game.modifiers;

public abstract class CostModifier implements CardModifier {
	public abstract int getModifiedCost(int cost);
	
	@Override
	public void accept(CardModifierVisitor visitor) {
		visitor.visit(this);		
	}	
}
