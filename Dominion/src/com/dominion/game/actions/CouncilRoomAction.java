package com.dominion.game.actions;

import com.dominion.game.GameMaster;
import com.dominion.game.Player;

public class CouncilRoomAction extends CardAction {
	@Override
	public void execute() {
		for (Player otherPlayer : GameMaster.getInstance().getPlayers()) {
			if (otherPlayer != player) {
				otherPlayer.getCardHand().addCard(otherPlayer.drawCard());
			}
		}
	}	
}
