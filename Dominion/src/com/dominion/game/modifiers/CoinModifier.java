package com.dominion.game.modifiers;

import com.dominion.game.cards.TreasureCard;

public abstract class CoinModifier implements CardModifier {
	public abstract int getModifiedCoin(TreasureCard card, int coin);

	@Override
	public void accept(CardModifierVisitor visitor) {
		visitor.visit(this);		
	}
}
