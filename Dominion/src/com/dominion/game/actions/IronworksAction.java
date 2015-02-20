package com.dominion.game.actions;

import java.util.List;

import com.dominion.game.GameState;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.TreasureCard;
import com.dominion.game.cards.VictoryCard;
import com.dominion.game.interfaces.messages.CardGainedMessage;

/**
 * 
 * @author user
 *
 * Gain a card costing up to 4 Coin. If it is an:
 * Action card, +1 Action;
 * Treasure card, +1 Coin;
 * Victory card, +1 Card.
 */
public class IronworksAction implements CardAction {
	@Override
	public void execute(GameState state) {
		
		List<Card> cards = state.listCardsFilterByCost(4);
		Card card = null;
		
		// Must gain a card
		while (card == null) {
			card = state.getCurrentPlayer().getCardToGain(cards, 4);
		}		
		
		state.getGameBoard().removeCardFromSupply(card.getClass());
		state.getCurrentPlayer().addCardToDiscardPile(card);
		state.broadcastToAllPlayers(new CardGainedMessage(state.getCurrentPlayer(), card));

		// Apply bonuses, noting that cards with multiple types add bonus each time
		if (card instanceof TreasureCard) {
			CardAction action = new PlusCoinAction(1);
			action.execute(state);
		}
		if (card instanceof ActionCard) {
			CardAction action = new PlusActionAction(1);
			action.execute(state);
		}
		if (card instanceof VictoryCard) {
			CardAction action = new PlusCardAction(1);
			action.execute(state);
		}			
	}	
}
