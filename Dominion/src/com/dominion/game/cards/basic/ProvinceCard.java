package com.dominion.game.cards.basic;

import com.dominion.game.cards.Card;
import com.dominion.game.cards.VictoryCard;
import com.dominion.game.visitors.CardVisitor;


public class ProvinceCard implements Card, VictoryCard {
	final static int POINTS = 6;

	@Override
	public boolean equals(Object obj) {
		// Only really card if card types match
		return this.getClass().isInstance(obj);
	}
	
	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public int getVictoryPoints() {
		return POINTS;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Province";
	}
	
	public static final int COST = 8;
	
	@Override
	public int getCost() {
		return COST;
	}	
}
