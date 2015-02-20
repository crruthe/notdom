package com.dominion.game.actions;

import com.dominion.game.GameState;
import com.dominion.game.cards.Card;
import com.dominion.game.interfaces.messages.CardRevealedMessage;
import com.dominion.game.interfaces.messages.GuessCardMessage;

/**
 * 
 * @author Vomit
 *
 * Choose two: +1 Card; +1 Action; +1 Buy; +1 Coin. (The choices must be different.)
 */
public class WishingWellAction implements CardAction {
	@Override
	public void execute(GameState state) {		
		Card card = state.getCurrentPlayer().drawCard();

		// User doesn't have any cards to draw
		if (card == null) {
			return;
		}

		Card guessCard = state.getCurrentPlayer().getCardWishingWell(state.listAllCards());
		
		state.broadcastToAllPlayers(new GuessCardMessage(state.getCurrentPlayer(), guessCard));		
		state.broadcastToAllPlayers(new CardRevealedMessage(state.getCurrentPlayer(), card));
		
		if (guessCard.equals(card)) {
			state.getCurrentPlayer().addCardToHand(card);
		} else {
			state.getCurrentPlayer().addCardToCardDeck(card);
		}
	}
}
