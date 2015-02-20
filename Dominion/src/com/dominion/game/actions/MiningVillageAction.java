package com.dominion.game.actions;

import com.dominion.game.GameState;
import com.dominion.game.cards.Card;
import com.dominion.game.interfaces.messages.CardTrashedMessage;

/**
 * You may trash this card immediately. If you do, +2 Coins.
 * 
 * @author user
 *
 */
public class MiningVillageAction implements CardAction {
	private Card trashCard;
	
	public MiningVillageAction(Card trashCard) {
		this.trashCard = trashCard;
	}

	@Override
	public void execute(GameState state) {
		
		// If this card has already been trashed (e.g. ThroneRoom)
		if (trashCard == null) {
			return;
		}
		
		if (state.getCurrentPlayer().wantsToTrashCard(trashCard)) {
			
			state.broadcastToAllPlayers(new CardTrashedMessage(state.getCurrentPlayer(), trashCard));
			
			state.getCurrentPlayer().removeFromPlayArea(trashCard);
			state.getGameBoard().addToTrashPile(trashCard);	
			
			new PlusCoinAction(2).execute(state);
			
			// Only allow trash card onces (e.g. ThroneRoom)
			trashCard = null;
		}
	}	
}
