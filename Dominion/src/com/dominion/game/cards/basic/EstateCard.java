package com.dominion.game.cards.basic;

import com.dominion.game.cards.Card;
import com.dominion.game.cards.VictoryCard;
import com.dominion.game.visitors.CardVisitor;


public class EstateCard implements Card, VictoryCard {
	public static final int COST = 2;
	public static final String NAME = "Estate";
	final static int POINTS = 1;
	
	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public boolean equals(Object obj) {
		// Cards are the same if names match (even if different instances)
		return NAME == ((Card)obj).getName();
	}

	@Override
	public int getCost() {
		return COST;
	}
	
	@Override
	public String getName() {
		return NAME;
	}
	
	@Override
	public int getVictoryPoints() {
		return POINTS;
	}	
}
