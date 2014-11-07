package com.dominion.game.cards.basic;

import com.dominion.game.cards.Card;
import com.dominion.game.cards.TreasureCard;
import com.dominion.game.visitors.CardVisitor;

public class SilverCard implements Card, TreasureCard {
	private final int COINS = 2;
	
	@Override
	public boolean equals(Object obj) {
		// Only really card if card types match
		return this.getClass().isInstance(obj);
	}
	
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
		return "Silver";
	}

	public static final int COST = 3;
	
	@Override
	public int getCost() {
		return COST;
	}
}
