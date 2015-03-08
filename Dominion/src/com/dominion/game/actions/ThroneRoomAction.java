package com.dominion.game.actions;

import com.dominion.game.GameState;
import com.dominion.game.cards.Card;
import com.dominion.game.interfaces.messages.CardPlayedMessage;

public class ThroneRoomAction implements CardAction {
	@Override
	public void execute(GameState state) {
		Card actionCard = (Card)state.getCurrentPlayer().getActionCardToPlay();
		
		if (actionCard != null) {
			state.getCurrentPlayer().moveCardFromHandToPlayArea(actionCard);			
			state.broadcastToAllPlayers(new CardPlayedMessage(state.getCurrentPlayer(), actionCard));
			
			// Player action card twice
			actionCard.onPlay(state);
			actionCard.onPlay(state);
		}
	}	
}
