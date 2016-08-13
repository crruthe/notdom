package com.dominion.game.actions;

import java.util.HashMap;
import com.dominion.game.GameState;
import com.dominion.game.Player;
import com.dominion.game.cards.Card;

/**
 * Each player passes a card in their hand to the player on their left. You may trash a card from your hand.
 * 
 * @author user
 *
 */
public class MasqueradeAction implements CardAction {
	@Override
	public void execute(GameState state) {
		
		// Match player to card set aside
		HashMap<Player, Card> cardsAside = new HashMap<Player, Card>();
		
		int numPlayers = state.getPlayers().size();
		
		// Player to the left is the next player i.e. (player+1)%numPlayers
		for (int i=0, j=1; i < numPlayers; i++, j=(i+1)%(numPlayers)) {
			Player player = state.getPlayers().get(i);			
			Player playerLeft = state.getPlayers().get(j);
			
			// If the player has no cards to pass
			if (player.getHandSize() == 0) {
				break;
			}
			
			Card card = null;
			while (card == null) {
				card = player.getCardToPassLeft();
			}
			
			player.removeFromHand(card);
			cardsAside.put(playerLeft, card);
		}
		
		// Place all cards set aside into the player's to the left hand
		for (Player player : cardsAside.keySet()) {
			player.addCardToHand(cardsAside.get(player));
		}
	}	
}
