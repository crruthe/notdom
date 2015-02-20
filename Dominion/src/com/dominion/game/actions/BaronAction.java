package com.dominion.game.actions;

import com.dominion.game.GameState;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.basic.EstateCard;
import com.dominion.game.interfaces.messages.CardGainedMessage;

/**
 * You may discard an Estate card. If you do, +4 Coins. Otherwise, gain an Estate card.
 * 
 * @author user
 *
 */
public class BaronAction implements CardAction {
	@Override
	public void execute(GameState state) {		
		
		Card discardCard = state.getCurrentPlayer().getEstateCardToDiscard();
		
		// If they selected an estate, discard it and give them +4 coins
		if (discardCard != null) {

			state.getCurrentPlayer().discardCardFromHand(discardCard);

			CardAction action = new PlusCoinAction(4);
			action.execute(state);
			
		} else {
			// Otherwise, they gain an estate
			Card card = state.getGameBoard().removeCardFromSupply(EstateCard.class);
			
			// Stack is empty
			if (card == null) {
				return;
			}
			
			state.getCurrentPlayer().addCardToDiscardPile(card);
			
			state.broadcastToAllPlayers(new CardGainedMessage(state.getCurrentPlayer(), card));
		}
	}	
}
