package com.dominion.game.visitors;

import com.dominion.game.cards.TreasureCard;

public class CountTotalCoinsVisitor extends CardVisitor {
	private int coins;
	
	/**
	 * @return the coins
	 */
	public int getCoins() {
		return coins;
	}

	@Override
	public void visit(TreasureCard treasureCard) {
		coins += treasureCard.getCoinAmount();
	}
}
