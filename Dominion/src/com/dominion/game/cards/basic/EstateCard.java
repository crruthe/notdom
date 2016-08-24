package com.dominion.game.cards.basic;

import com.dominion.game.cards.Card;
import com.dominion.game.cards.VictoryCard;
import com.dominion.game.visitors.CardVisitor;


public class EstateCard extends Card implements VictoryCard {

	private static final int POINTS = 1;

	public EstateCard() {
		super("Estate", 2);
	}
	
	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public int getVictoryPoints() {
		return POINTS;
	}	
}
