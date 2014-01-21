package com.dominion.game.cards.basic;

import com.dominion.game.cards.Card;
import com.dominion.game.cards.VictoryCard;
import com.dominion.game.visitors.CardVisitor;


public class EstateCard implements Card, VictoryCard {
	final static int POINTS = 1;
	
	@Override
	public int getVictoryPoints() {
		return POINTS;
	}

	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Estate";
	}
	
	public static final int COST = 2;
	
	@Override
	public int getCost() {
		return COST;
	}	
}
