package com.dominion.game.cards.basic;

import com.dominion.game.cards.Card;
import com.dominion.game.visitors.CardVisitor;

public class CurseCard extends Card {
	
	public CurseCard() {
		super("Curse", 0);
	}

	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit(this);
	}
}
