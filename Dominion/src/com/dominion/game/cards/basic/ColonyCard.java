package com.dominion.game.cards.basic;

import com.dominion.game.cards.Card;
import com.dominion.game.cards.VictoryCard;
import com.dominion.game.visitors.CardVisitor;


public class ColonyCard extends Card implements VictoryCard {
	public static final int COST = 11;
	public static final String NAME = "Colony";
	public static final int POINTS = 10;
	
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
	
	@Override
	public int getVictoryPoints() {
		return POINTS;
	}	
}
