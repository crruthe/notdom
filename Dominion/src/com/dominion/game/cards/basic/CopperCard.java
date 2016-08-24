package com.dominion.game.cards.basic;

import com.dominion.game.cards.Card;
import com.dominion.game.cards.TreasureCard;
import com.dominion.game.visitors.CardVisitor;

public class CopperCard extends Card implements TreasureCard {
	
	private static final int COINS = 1;

	public CopperCard() {
		super("Copper", 0);
	}

	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public int getCoinAmount() {
		return COINS;
	}
}
