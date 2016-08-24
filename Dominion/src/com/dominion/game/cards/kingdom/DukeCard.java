package com.dominion.game.cards.kingdom;

import com.dominion.game.cards.Card;
import com.dominion.game.cards.VictoryCard;
import com.dominion.game.visitors.CardVisitor;


public class DukeCard extends Card implements VictoryCard {	
	
	public static final int POINTS = 0;
	public static final int POINTS_PER_DUCHY = 1;
	
	public DukeCard() {
		super("Duke", 5);
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
