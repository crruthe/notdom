package com.dominion.game.cards.basic;

import com.dominion.game.cards.Card;
import com.dominion.game.cards.TreasureCard;
import com.dominion.game.visitors.CardVisitor;

public class GoldCard extends Card implements TreasureCard {
	
	private static final int COINS = 3;

	public GoldCard() {
		super("Gold", 6);
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
