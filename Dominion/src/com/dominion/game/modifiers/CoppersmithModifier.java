package com.dominion.game.modifiers;

import com.dominion.game.cards.TreasureCard;
import com.dominion.game.cards.basic.CopperCard;

public class CoppersmithModifier extends CoinModifier {

	@Override
	public int getModifiedCoin(TreasureCard card, int coin) {
		if (card instanceof CopperCard) {
			return coin + 1;
		}
		return coin;
	}
}
