package com.dominion.game.cards.basic;

import com.dominion.game.cards.Card;
import com.dominion.game.cards.TreasureCard;
import com.dominion.game.visitors.CardVisitor;


public class CopperCard implements Card, TreasureCard {
	private final int COINS = 1;
	
	@Override
	public int getCoinAmount() {		
		return COINS;
	}

	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit(this);		
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Copper";
	}

	public static final int COST = 0;
	
	@Override
	public int getCost() {
		return COST;
	}	
}
