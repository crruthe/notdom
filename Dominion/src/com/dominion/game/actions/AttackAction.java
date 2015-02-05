package com.dominion.game.actions;

import com.dominion.game.Player;
import com.dominion.game.cards.ReactionCard;
import com.dominion.game.interfaces.messages.CardRevealedMessage;

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
					player.broadcastMessage(new CardRevealedMessage(otherPlayer, card));
		
					action.setPlayer(otherPlayer);
					action.execute();
				}				
			} while (card != null);

			// If they played a moat, they are immune to attacks
			if (!otherPlayer.isImmune()) {
				executeAttackOnPlayer(otherPlayer);
			}
		}
	}
	
	abstract public void executeAttackOnPlayer(Player player);
}
