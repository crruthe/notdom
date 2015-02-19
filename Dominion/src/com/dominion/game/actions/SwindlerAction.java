package com.dominion.game.actions;

import java.util.List;

import com.dominion.game.GameState;
import com.dominion.game.Player;
import com.dominion.game.cards.Card;
import com.dominion.game.interfaces.messages.CardGainedMessage;
import com.dominion.game.interfaces.messages.CardTrashedMessage;

/**
 * Each other player trashes the top card of his deck and gains a card with the same cost that you choose.
 * 
 * @author user
 *
 */
public class SwindlerAction extends AttackAction {
	@Override
	public void executeAttackOnPlayer(GameState state, Player victim) {		
		// Draw the card off the top of the deck and trash it
		Card trashCard = victim.drawCard();

		// If the victim has no cards to draw
		if (trashCard == null) {
			return;
		}
		
		state.broadcastToAllPlayers(new CardTrashedMessage(victim, trashCard));

		state.getGameBoard().addToTrashPile(trashCard);					

		// The victim player gains a card of your choice for exactly the same cost
		int cost = trashCard.modifyCard(state.getTurnState().getModifiers()).getCost();

		List<Card> cards = state.listCardsFilterByCost(cost, cost);
		Card card = state.getCurrentPlayer().getCardToBuy(cards);
		
		if (card != null) {
			state.getGameBoard().removeCardFromSupply(card.getClass());
			victim.addCardToDiscardPile(card);
			state.broadcastToAllPlayers(new CardGainedMessage(victim, card));
		}
	}	
}
