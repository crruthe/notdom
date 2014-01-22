package com.dominion.game.actions;

import com.dominion.game.Player;
import com.dominion.game.cards.ReactionCard;

public abstract class AttackAction extends CardAction {

	@Override
	public void execute() {
		// Allow the other players to reveal reaction cards
		for (Player otherPlayer : player.getOtherPlayers()) {
			
			// Other players can keep revealing reaction cards
			ReactionCard card;			
			do {
				// Allow the player to reveal any reaction cards
				card = otherPlayer.getPlayerInterface().getReactionCardToPlay();
				if (card != null) {
					CardAction action = card.getReaction();
		
					action.setPlayer(otherPlayer);
					action.execute();
				}
			} while (card != null);
		}
		
		// Now attack the other players
		for (Player otherPlayer : player.getOtherPlayers()) {
			
			// If they didn't play a moat reaction card, attack them!
			if (!otherPlayer.isImmune()) {
				executeAttackOnPlayer(otherPlayer);
			}
			
			// Attack is over, reset the player immune status
			otherPlayer.setImmune(false);			
		}
	}
	
	abstract public void executeAttackOnPlayer(Player player);
}
