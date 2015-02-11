package com.dominion.game.actions;

import com.dominion.game.Player;
import com.dominion.game.cards.ReactionCard;
import com.dominion.game.interfaces.messages.CardRevealedMessage;

public abstract class AttackAction implements CardAction {

	@Override
	public void execute(Player player) {
		// Allow the other players to reveal reaction cards
		for (Player victim : player.getOtherPlayers()) {
			
			// Other players can keep revealing reaction cards
			ReactionCard card;
			do {
				// Allow the player to reveal any reaction cards
				card = victim.getReactionCardToPlay();
				if (card != null) {
					CardAction action = card.getReaction();
					player.invokeMessageAll(new CardRevealedMessage(victim, card));
		
					action.execute(victim);
				}				
			} while (card != null);

			// If they played a moat, they are immune to attacks
			if (!victim.isImmune()) {
				executeAttackOnPlayer(player, victim);
			}
		}
	}
	
	abstract public void executeAttackOnPlayer(Player attacker, Player victim);
}
