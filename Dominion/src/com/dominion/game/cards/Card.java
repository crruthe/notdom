package com.dominion.game.cards;

import com.dominion.game.visitors.CardVisitor;

public interface Card {
	public void accept(CardVisitor visitor);
	public String getName();
	public int getCost();
}
