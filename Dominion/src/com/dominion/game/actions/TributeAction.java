package com.dominion.game.actions;

import java.util.LinkedList;
import java.util.List;

import com.dominion.game.GameState;
import com.dominion.game.Player;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.TreasureCard;
import com.dominion.game.cards.VictoryCard;
import com.dominion.game.interfaces.messages.CardRevealedMessage;

/**
 * 
 * @author user
 *
 * The player to your left reveals then discards the top 2 cards of his deck. 
 * For each differently named card revealed, if it is an:
 * Action Card; +2 Actions; 
 * Treasure Card; +2 Coins; 
 * Victory Card; +2 Cards.
 */
public class TributeAction implements CardAction {
	@Override
	public void execute(GameState state) {
		
		List<Card> cards = new LinkedList<Card>();
		
		// Current player is index(0), so next player is index(1)
		Player playerToLeft = state.getPlayers().get(1);
		
		// Reveal the top two cards of their deck
		for (int i = 0; i < 2; i++) {
			Card card = playerToLeft.drawCard();
			
			// User doesn't have anymore cards
			if (card == null) {
				break;
			}
			
			state.broadcastToAllPlayers(new CardRevealedMessage(playerToLeft, card));			

			playerToLeft.addCardToDiscardPile(card);
			
			// Only add "differently named" cards
			if (!cards.contains(card)) {
				cards.add(card);
			}			
		}
		
		// Apply bonuses, noting that cards with multiple types add bonus each time
		for (Card card : cards) {
			if (card instanceof TreasureCard) {
				CardAction action = new PlusCoinAction(2);
				action.execute(state);
			}
			if (card instanceof ActionCard) {
				CardAction action = new PlusActionAction(2);
				action.execute(state);
			}
			if (card instanceof VictoryCard) {
				CardAction action = new PlusCardAction(2);
				action.execute(state);
			}			
		}
	}	
}
