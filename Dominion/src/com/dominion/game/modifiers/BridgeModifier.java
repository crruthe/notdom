package com.dominion.game.modifiers;

import com.dominion.game.cards.Card;

public class BridgeModifier implements CardModifier {

	@Override
	public Card modify(Card card) {
		return new BridgeModifiedCard(card);
	}
}
