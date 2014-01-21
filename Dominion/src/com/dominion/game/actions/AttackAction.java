package com.dominion.game.actions;

import com.dominion.game.GameMaster;
import com.dominion.game.Player;

public abstract class AttackAction extends CardAction {

	@Override
	public void execute() {
		for (Player otherPlayer : GameMaster.getInstance().getPlayers()) {
			if (otherPlayer != player) {
				// Allow the player to reveal any reaction cards
				otherPlayer.revealReactionPhase();
				
				// If they didn't play a moat reaction card, attack them!
				if (!otherPlayer.isImmune()) {
					executeAttackOnPlayer(otherPlayer);
				}
				
				// Attack is over, reset the player immune status
				otherPlayer.setImmune(false);
			}
		}	
	}
	
	abstract public void executeAttackOnPlayer(Player player);
}
