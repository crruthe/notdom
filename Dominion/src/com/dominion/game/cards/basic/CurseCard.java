package com.dominion.game.cards.basic;

import com.dominion.game.cards.Card;
import com.dominion.game.visitors.CardVisitor;

public class CurseCard implements Card {
	public static final int COST = 0;
	public static final String NAME = "Curse";
	
	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public boolean equals(Object obj) {
		// Cards are the same if names match (even if different instances)
		return NAME.equals(((Card)obj).getName());
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
