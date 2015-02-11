package com.dominion.game.cards.basic;

import com.dominion.game.cards.Card;
import com.dominion.game.cards.VictoryCard;
import com.dominion.game.visitors.CardVisitor;


public class ProvinceCard extends Card implements VictoryCard {
	public static final int COST = 8;
	public static final String NAME = "Province";
	public static final int POINTS = 6;
	
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
