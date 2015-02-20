package com.dominion.game.modifiers;

import com.dominion.game.cards.TreasureCard;
import com.dominion.game.cards.basic.CopperCard;

public class CoppersmithModifier extends CardModifier {

	@Override
	public TreasureCard modify(TreasureCard card) {
		if (card instanceof CopperCard)
			return new CoppersmithModifiedCard((CopperCard)card);
		else
			return card;
	}
}
