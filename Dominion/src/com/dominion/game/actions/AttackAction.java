package com.dominion.game.actions;

import com.dominion.game.GameState;
import com.dominion.game.Player;
import com.dominion.game.cards.ReactionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.interfaces.messages.CardRevealedMessage;

public abstract class AttackAction implements CardAction {

	@Override
	public void execute(GameState state) {
		// Allow the other players to reveal reaction cards
		for (Player victim : state.getOtherPlayers()) {
			
			// Other players can keep revealing reaction cards
			ReactionCard card;
			do {
				// Allow the player to reveal any reaction cards
				card = victim.getReactionCardToPlay();
				if (card != null) {
					ReactionAction action = card.getReaction();
					state.broadcastToAllPlayers(new CardRevealedMessage(victim, (Card)card));
		
					action.executeOnPlayer(state, victim);
				}				
			} while (card != null);

			// If they played a moat, they are immune to attacks
			if (!victim.isImmune()) {
				executeAttackOnPlayer(state, victim);
			}
		}
	}
	
	abstract public void executeAttackOnPlayer(GameState state, Player victim);
}
