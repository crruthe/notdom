package com.dominion.game.actions;

import com.dominion.game.GameState;
import com.dominion.game.cards.Card;
import com.dominion.game.interfaces.messages.CardTrashedMessage;


/**
 * @author user
 *
 * Trash this card. Gain a card costing up to 5 coin.
 */
public class FeastAction implements CardAction {
	private Card trashCard;
	
	public FeastAction(Card trashCard) {
		this.trashCard = trashCard;
	}

	@Override
	public void execute(GameState state) {

		state.broadcastToAllPlayers(new CardTrashedMessage(state.getCurrentPlayer(), trashCard));
		
		state.getCurrentPlayer().removeFromPlayArea(trashCard);
		state.getGameBoard().addToTrashPile(trashCard);
		
		new GainCardAction(5, true).execute(state);
	}	
}
