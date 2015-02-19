package com.dominion.game.modifiers;

import com.dominion.game.cards.Card;

public interface CardModifier {
	public Card modify(Card card);
}
