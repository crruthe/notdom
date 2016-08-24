package com.dominion.game.cards.basic;

import com.dominion.game.cards.Card;
import com.dominion.game.cards.TreasureCard;
import com.dominion.game.visitors.CardVisitor;

public class SilverCard extends Card implements TreasureCard {
	
	private static final int COINS = 2;

	public SilverCard() {
		super("Silver", 3);
	}
	
	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public int getCoinAmount() {		
		return COINS;
	}
}
