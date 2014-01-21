package com.dominion.game.cards.basic;

import com.dominion.game.cards.Card;
import com.dominion.game.cards.TreasureCard;
import com.dominion.game.visitors.CardVisitor;

public class GoldCard implements Card, TreasureCard {
	private final int COINS = 3;
	
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
		return "Gold";
	}
	
	public static final int COST = 6;
	
	@Override
	public int getCost() {
		return COST;
	}
}
