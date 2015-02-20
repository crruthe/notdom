package com.dominion.game.actions;

import java.util.Collection;

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
			
			// CardAction can have state and it's important to track it (e.g. Mining Village allow trash once)
			Collection<CardAction> actions = actionCard.buildActionList();
			
			// Track actions player for Conspirator
			state.getTurnState().incrementActionsPlayed();
			
			for (CardAction action : actions) {
				action.execute(state);
			}			

			// Track actions player for Conspirator
			state.getTurnState().incrementActionsPlayed();

			for (CardAction action : actions) {
				action.execute(state);
			}
		}
	}	
}
