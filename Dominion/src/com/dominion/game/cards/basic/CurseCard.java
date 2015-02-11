package com.dominion.game.cards.basic;

import com.dominion.game.cards.Card;
import com.dominion.game.visitors.CardVisitor;

public class CurseCard extends Card {
	public static final int COST = 0;
	public static final String NAME = "Curse";
	
	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public int getCost() {
		return COST;
	}

	@Override
	public String getName() {
		return NAME;
	}	
}
