package com.dominion.game.modifiers;

import com.dominion.game.cards.Card;
import com.dominion.game.cards.TreasureCard;

public abstract class CardModifier {
	
	public Card modify(Card card) {
		return card;
	}
	
	public TreasureCard modify(TreasureCard card) {
		return card;
	}
}
