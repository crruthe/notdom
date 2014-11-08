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
				card = otherPlayer.getReactionCardToPlay();
				if (card != null) {
					CardAction action = card.getReaction();
		
					action.setPlayer(otherPlayer);
					action.execute();
				}
				
				// If card doesn't provide immunity (e.g. moat card), allow attack
				if (card == null || !card.grantsImmunity()) {
					executeAttackOnPlayer(otherPlayer);
				}
			} while (card != null);
		}
	}
	
	abstract public void executeAttackOnPlayer(Player player);
}
