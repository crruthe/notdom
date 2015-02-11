package com.dominion.game.actions;

import com.dominion.game.Player;
import com.dominion.game.cards.ActionCard;

public class ThroneRoomAction implements CardAction {
	@Override
	public void execute(Player player) {
		ActionCard actionCard = player.getActionCardToPlay();
		
		if (actionCard != null) {
			player.playCard(actionCard);
			
			for (CardAction action : actionCard.buildActionList()) {
				action.execute(player);
			}			

			for (CardAction action : actionCard.buildActionList()) {
				action.execute(player);
			}
		}
	}	
}
