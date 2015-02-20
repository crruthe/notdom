package com.dominion.game.actions;

import com.dominion.game.GameState;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.interfaces.messages.CardPlayedMessage;

public class ThroneRoomAction implements CardAction {
	@Override
	public void execute(GameState state) {
		ActionCard actionCard = state.getCurrentPlayer().getActionCardToPlay();
		
		if (actionCard != null) {
			state.getCurrentPlayer().moveCardFromHandToPlayArea((Card)actionCard);			
			state.broadcastToAllPlayers(new CardPlayedMessage(state.getCurrentPlayer(), (Card)actionCard));
			
			// Track actions player for Conspirator
			state.getTurnState().incrementActionsPlayed();
			
			for (CardAction action : actionCard.buildActionList()) {
				action.execute(state);
			}			

			// Track actions player for Conspirator
			state.getTurnState().incrementActionsPlayed();

			for (CardAction action : actionCard.buildActionList()) {
				action.execute(state);
			}
		}
	}	
}
