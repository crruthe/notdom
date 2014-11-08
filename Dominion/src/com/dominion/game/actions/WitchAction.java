package com.dominion.game.actions;

import com.dominion.game.Player;
import com.dominion.game.cards.basic.CurseCard;

public class WitchAction extends AttackAction {
	@Override
	public void executeAttackOnPlayer(Player attackPlayer) {
		attackPlayer.gainCardFromSupply(CurseCard.NAME);
	}
}
