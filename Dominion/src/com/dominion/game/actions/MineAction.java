package com.dominion.game.actions;

import java.util.List;

import com.dominion.game.GameState;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.TreasureCard;
import com.dominion.game.interfaces.messages.CardTrashedMessage;

/**
 * 
 * @author Vomit
 *
 * Trash a Treasure card from your hand. Gain a Treasure card costing up to 3 Coins more; put it into your hand.
 */
public class MineAction implements CardAction {
	@Override
	public void execute(GameState state) {
		Card trashCard = (Card)state.getCurrentPlayer().getTreasureCardToTrash();
		if (trashCard == null) {
			return;
		}
				
		state.broadcastToAllPlayers(new CardTrashedMessage(state.getCurrentPlayer(), trashCard));
		
		state.getCurrentPlayer().removeFromHand(trashCard);
		state.getGameBoard().addToTrashPile(trashCard);

		// Get actual cost after modifiers e.g. Bridge
		int cost = trashCard.modifyCard(state.getTurnState().getModifiers()).getCost();

		// Find a treasure card to replace the trashed card
		List<Card> cards = state.listCardsFilterByClassAndCost(TreasureCard.class, cost + 3);
		Card card = null;
		while (card == null) {
			card = state.getCurrentPlayer().getTreasureCardToGain(cards);			
		}	
		
		state.getCurrentPlayer().addCardToHand(card);
	}
}
