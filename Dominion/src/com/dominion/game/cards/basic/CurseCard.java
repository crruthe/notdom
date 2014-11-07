package com.dominion.game.cards.basic;

import com.dominion.game.cards.Card;
import com.dominion.game.visitors.CardVisitor;

public class CurseCard implements Card {
	public static final int COST = 0;
	
	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Curse";
	}

	@Override
	public boolean equals(Object obj) {
		// Only really card if card types match
		return this.getClass().isInstance(obj);
	}

	@Override
	public int getCost() {
		return COST;
	}	
}
