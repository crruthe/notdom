package com.dominion.game.actions;

import java.util.List;

import com.dominion.game.GameState;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.interfaces.messages.HandRevealedMessage;

/**
 * Reveal your hand. If you have no Action cards in hand, +2 Cards.
 * 
 * @author user
 *
 */
public class ShantyTownAction implements CardAction {
	@Override
	public void execute(GameState state) {
		
		List<Card> cards = state.getCurrentPlayer().getHand();
		
		state.broadcastToAllPlayers(new HandRevealedMessage(state.getCurrentPlayer(), cards));
		
		// Check if the player has any action cards. End the action if they do.
		for (Card card : cards) {
			if (card instanceof ActionCard) {
				return;
			}
		}
		
		// Otherwise, give them two cards
		CardAction action = new PlusCardAction(2);
		action.execute(state);
	}	
}
