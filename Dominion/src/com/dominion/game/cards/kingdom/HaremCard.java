package com.dominion.game.cards.kingdom;

import com.dominion.game.cards.Card;
import com.dominion.game.cards.VictoryCard;
import com.dominion.game.cards.TreasureCard;
import com.dominion.game.visitors.CardVisitor;


public class HaremCard extends Card implements VictoryCard, TreasureCard {	
	public static final int COST = 6;
	public static final String NAME = "Harem";
	public static final int POINTS = 2;
	private final int COINS = 2;
	
	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit((VictoryCard)this);
		visitor.visit((TreasureCard)this);
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

	@Override
	public int getCoinAmount() {
		return COINS;
	}	
}
