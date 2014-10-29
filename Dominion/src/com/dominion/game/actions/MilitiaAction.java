package com.dominion.game.actions;

import com.dominion.game.Player;
import com.dominion.game.cards.Card;

public class MilitiaAction extends AttackAction {
	@Override
	public void executeAttackOnPlayer(Player attackPlayer) {
		while (attackPlayer.getHandSize() > 3) {
			Card card = attackPlayer.getCardToDiscard();
			attackPlayer.discardCardFromHand(card);
		}
	}
}
