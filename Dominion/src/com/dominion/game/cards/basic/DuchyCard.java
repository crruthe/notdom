package com.dominion.game.cards.basic;

import com.dominion.game.cards.Card;
import com.dominion.game.cards.VictoryCard;
import com.dominion.game.visitors.CardVisitor;


public class DuchyCard extends Card implements VictoryCard {
	
	private final static int POINTS = 3;

	public DuchyCard() {
		super("Duchy", 5);
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
