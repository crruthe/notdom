package com.dominion.game.modifiers;


public interface CardModifier {
	public void accept(CardModifierVisitor visitor);
}
