package com.dominion.game.actions;

import com.dominion.game.GameState;
import com.dominion.game.Player;

public class CouncilRoomAction implements CardAction {
	@Override
	public void execute(GameState state) {
		for (Player otherPlayer : state.getOtherPlayers()) {
			otherPlayer.drawCardToHand();
		}
	}	
}
