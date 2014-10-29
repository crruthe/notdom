package com.dominion.game.actions;

import com.dominion.game.cards.ActionCard;

public class ThroneRoomAction extends CardAction {
	@Override
	public void execute() {
		ActionCard actionCard = player.getActionCardToPlay();
		
		if (actionCard != null) {
			player.playCard(actionCard);
			
			for (CardAction action : actionCard.buildActionList()) {
				action.setPlayer(player);
				action.execute();
			}			

			for (CardAction action : actionCard.buildActionList()) {
				action.setPlayer(player);
				action.execute();
			}
		}
	}	
}
