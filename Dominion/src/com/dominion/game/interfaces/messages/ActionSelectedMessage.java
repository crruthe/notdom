package com.dominion.game.interfaces.messages;

import com.dominion.game.Player;
import com.dominion.game.interfaces.PlayerInterface;

public class ActionSelectedMessage implements PlayerInterfaceMessage {

	private Player player;
	private String action;
	
	public ActionSelectedMessage(Player player, String action) {
		this.player = player;
		this.action = action;
	}
	
	@Override
	public void invoke(PlayerInterface playerInterface) {		
		playerInterface.notifyActionSelected(player, action);
	}
}
