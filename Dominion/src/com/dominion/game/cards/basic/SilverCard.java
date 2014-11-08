package com.dominion.game.cards.basic;

import com.dominion.game.cards.Card;
import com.dominion.game.cards.TreasureCard;
import com.dominion.game.visitors.CardVisitor;

public class SilverCard implements Card, TreasureCard {
	public static final int COST = 3;
	public static final String NAME = "Silver";
	private final int COINS = 2;
	
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
	public int getCoinAmount() {		
		return COINS;
	}

	@Override
	public int getCost() {
		return COST;
	}
	
	@Override
	public String getName() {
		return NAME;
	}
}
