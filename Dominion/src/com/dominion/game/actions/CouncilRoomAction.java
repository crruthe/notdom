package com.dominion.game.actions;

import com.dominion.game.Player;

public class CouncilRoomAction implements CardAction {
	@Override
	public void execute(Player player) {
		for (Player otherPlayer : player.getOtherPlayers()) {
			otherPlayer.drawCardToHand();
		}
	}	
}
