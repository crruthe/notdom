package com.dominion.game.actions;

import com.dominion.game.Player;

public class CouncilRoomAction extends CardAction {
	@Override
	public void execute() {
		for (Player otherPlayer : player.getOtherPlayers()) {
			otherPlayer.addCardToHand(otherPlayer.drawCard());
		}
	}	
}
