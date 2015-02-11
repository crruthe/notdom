package com.dominion.game.actions;

import com.dominion.game.Player;
import com.dominion.game.cards.Card;

public class MilitiaAction extends AttackAction {
	@Override
	public void executeAttackOnPlayer(Player attacker, Player victim) {
		while (victim.getHandSize() > 3) {
			Card card = victim.getCardToDiscard();
			if (card != null) {
				victim.discardCardFromHand(card);
			}			
		}
	}
}
