package com.dominion.game.actions;

import java.util.LinkedList;

import com.dominion.game.GameState;
import com.dominion.game.Player;
import com.dominion.game.cards.Card;
import com.dominion.game.interfaces.messages.CardRevealedMessage;
import com.dominion.game.interfaces.messages.CardTrashedMessage;

/**
 * Each other player reveals cards from the top of his deck until revealing one 
 * costing 3 Coins or more. He trashes that card and may gain a card costing at 
 * most 2 Coins less than it. He discards the other revealed cards.
 * 
 * @author user
 *
 */
public class SaboteurAction extends AttackAction {
	
	@Override
	public void executeAttackOnPlayer(GameState state, Player victim) {
		LinkedList<Card> setAside = new LinkedList<Card>();
		
		while (true) {
			// Victim has no more cards
			if (victim.getHandSize() == 0) {
				break;
			}
			Card card = victim.drawCard();

			state.broadcastToAllPlayers(new CardRevealedMessage(victim, card));			
			
			int cost = state.getModifiedCost(card);
			
			if (cost >= 3) {
				state.broadcastToAllPlayers(new CardTrashedMessage(victim, card));
				
				state.getGameBoard().addToTrashPile(card);
				
				// They may gain a card costing 2 less 
				new GainCardAction(victim, cost-2, false).execute(state);
				
				// once they have revealed a card of cost 3 or more, they can stop
				break;				
			} else {
				setAside.add(card);
			}			
		}
		
		victim.addCardsToDiscardPile(setAside);
	}	
}
