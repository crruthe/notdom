package com.dominion.game.cards.kingdom;

import com.dominion.game.cards.Card;
import com.dominion.game.cards.VictoryCard;
import com.dominion.game.visitors.CardVisitor;


public class GardensCard implements Card, VictoryCard {	
	public static final int COST = 4;
	public static final String NAME = "Gardens";
	public static final int POINTS = 6;
	
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
	
	@Override
	public int getVictoryPoints() {
		return POINTS;
	}	
}
