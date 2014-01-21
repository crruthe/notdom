package com.dominion.game.actions;

import com.dominion.game.Player;
import com.dominion.game.cards.Card;

public class MilitiaAction extends AttackAction {
	@Override
	public void executeAttackOnPlayer(Player attackPlayer) {
		while (attackPlayer.getCardHand().getSize() > 3) {
			Card card = attackPlayer.getPlayerInterface().getCardToDiscard();
			attackPlayer.discardCard(card);
		}
	}
}
