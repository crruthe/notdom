package com.dominion.game.actions;

import com.dominion.game.Player;
import com.dominion.game.cards.Card;

public class WitchAction extends AttackAction {
	@Override
	public void executeAttackOnPlayer(Player attackPlayer) {
		Card curseCard = player.getGameBoard().getCurseCard();
		attackPlayer.gainCard(curseCard);
	}
}
