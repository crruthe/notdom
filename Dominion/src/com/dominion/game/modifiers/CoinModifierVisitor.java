package com.dominion.game.modifiers;

import com.dominion.game.cards.TreasureCard;

public class CoinModifierVisitor extends CardModifierVisitor {
	
	private int coin;
	private TreasureCard card;
	
	public CoinModifierVisitor(TreasureCard card) {
		this.card = card;
		this.coin = card.getCoinAmount();
	}
	
	public void visit(CoinModifier modifier) {
		this.coin = modifier.getModifiedCoin(card, coin);
	}
	
	public int getCoin() {
		return coin;
	}
}
