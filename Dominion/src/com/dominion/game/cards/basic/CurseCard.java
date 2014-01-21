package com.dominion.game.cards.basic;

import com.dominion.game.cards.Card;
import com.dominion.game.visitors.CardVisitor;

public class CurseCard implements Card {
	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Curse";
	}

	public static final int COST = 0;
	
	@Override
	public int getCost() {
		return COST;
	}	
}
