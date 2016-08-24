package com.dominion.game.cards.kingdom;

import com.dominion.game.cards.Card;
import com.dominion.game.cards.VictoryCard;
import com.dominion.game.visitors.CardVisitor;


public class GardensCard extends Card implements VictoryCard {	
	
	public static final int CARDS_PER_POINT = 10;
	public static final int POINTS = 0;
	
	public GardensCard() {
		super("Gardens", 4);
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
