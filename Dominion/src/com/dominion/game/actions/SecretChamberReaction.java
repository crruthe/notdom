package com.dominion.game.actions;

import com.dominion.game.GameState;
import com.dominion.game.Player;
import com.dominion.game.cards.Card;

/**
 * When another player plays an Attack card, you may reveal this from your hand. 
 * If you do, +2 Cards, then put 2 cards from your hand on top of your deck.
 * 
 * @author user
 *
 */
public class SecretChamberReaction implements ReactionAction {
	
	@Override
	public void executeOnPlayer(GameState state, Player player) {
		CardAction action = new PlusCardAction(player, 2);
		action.execute(state);
		
		for (int i=0; i<2; i++) {
			if (player.getHandSize() == 0)
				break;			
			Card card = null;
			while (card == null) {
				card = player.getCardToPutOnDeck();
			}			
			player.moveCardFromHandToDeck(card);
		}
	}

	@Override
	public void execute(GameState state) {		
		// Not the current player, so do nothing.
	}	
}
