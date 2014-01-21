package com.dominion.game.cards.kingdom;

import com.dominion.game.cards.Card;
import com.dominion.game.cards.VictoryCard;
import com.dominion.game.visitors.CardVisitor;


public class GardensCard implements Card, VictoryCard {
	@Override
	public int getVictoryPoints() {
		return 0;
	}

	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Gardens";
	}
	
	public static final int COST = 4;
	
	@Override
	public int getCost() {
		return COST;
	}	
}
